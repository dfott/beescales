package de.beescales.scheduled;

import de.beescales.data.ScaleThreshold;
import de.beescales.dto.ThresholdDataView;
import de.beescales.repository.ScaleDataRepository;
import de.beescales.service.EmailService;
import de.beescales.service.ScaleThresholdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(value = "scheduled.active", havingValue = "true")
@AllArgsConstructor
public class WeightCron {

  private final ScaleDataRepository scaleDataRepository;
  private final ScaleThresholdService scaleThresholdService;
  private final EmailService emailService;

  @Scheduled(fixedRateString = "${scheduled.fixed-rate.weight}", timeUnit = TimeUnit.SECONDS)
  void checkRelativeWeight() {
    log.info("Start checking relative weight thresholds...");
    List<ThresholdDataView> data = scaleDataRepository.findRelativeByThreshold();

    Map<Integer, List<ThresholdDataView>> groupedData = groupData(data);

    for (Map.Entry<Integer, List<ThresholdDataView>> entry : groupedData.entrySet()) {
      if (entry.getValue().size() >= 2) {
        ThresholdDataView firstEntry = entry.getValue().get(0);
        ThresholdDataView lastEntry = entry.getValue().get(entry.getValue().size() - 1);
        double diff = lastEntry.getWeight() - firstEntry.getWeight();
        if (diff < firstEntry.getMin() || diff > firstEntry.getMax()) {
          try {
            emailService.sendRelativeWeightAlert(
                entry.getKey(), firstEntry.getMin(), lastEntry.getMax(), entry.getValue());
            ScaleThreshold threshold = scaleThresholdService.getThreshold(entry.getKey());
            threshold.setActive(false);
            scaleThresholdService.updateThreshold(threshold.getId(), threshold);
          } catch (Exception e) {
            log.error("Failed to send e-mail. {}", e);
          }
        }
      }
    }
    log.info("Finished checking the relative weight thresholds.");
  }

  @Scheduled(fixedRateString = "${scheduled.fixed-rate.weight}", timeUnit = TimeUnit.SECONDS)
  void checkAbsoluteWeight() {
    log.info("Start checking absolute weight thresholds...");
    List<ThresholdDataView> data = scaleDataRepository.findAbsoluteByThreshold();

    Map<Integer, List<ThresholdDataView>> groupedData = groupData(data);

    for (Map.Entry<Integer, List<ThresholdDataView>> entry : groupedData.entrySet()) {
      List<ThresholdDataView> lowerThanMin =
          filterData(entry.getValue(), tdv -> tdv.getWeight() <= tdv.getMin());
      List<ThresholdDataView> greaterThanMax =
          filterData(entry.getValue(), tdv -> tdv.getWeight() >= tdv.getMax());
      if (lowerThanMin.size() >= 2 || greaterThanMax.size() >= 2) {
        ThresholdDataView exampleVal = entry.getValue().get(0);
        try {
          emailService.sendAbsoluteWeightAlert(
              entry.getKey(), exampleVal.getMin(), exampleVal.getMax(), entry.getValue());
          ScaleThreshold threshold = scaleThresholdService.getThreshold(entry.getKey());
          threshold.setActive(false);
          scaleThresholdService.updateThreshold(entry.getKey(), threshold);
        } catch (Exception e) {
          log.error("Failed to send e-mail. {}", e);
        }
      }
    }
    log.info("Finished checking the absolute weight thresholds.");
  }

  private List<ThresholdDataView> filterData(
      List<ThresholdDataView> data, Predicate<ThresholdDataView> predicate) {
    return data.stream().filter(predicate).collect(Collectors.toList());
  }

  private Map<Integer, List<ThresholdDataView>> groupData(List<ThresholdDataView> data) {
    return data.stream()
        .collect(
            Collectors.groupingBy(
                ThresholdDataView::getDeviceid,
                Collectors.mapping(thresholdDataView -> thresholdDataView, Collectors.toList())));
  }
}
