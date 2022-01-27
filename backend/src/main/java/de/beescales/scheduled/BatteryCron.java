package de.beescales.scheduled;

import de.beescales.dto.BatteryCronView;
import de.beescales.repository.ScaleDataRepository;
import de.beescales.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(value = "scheduled.active", havingValue = "true")
@AllArgsConstructor
public class BatteryCron {

  private final ScaleDataRepository scaleDataRepository;
  private final EmailService emailService;

  @Scheduled(fixedRateString = "${scheduled.fixed-rate.battery}", timeUnit = TimeUnit.SECONDS)
  void checkBatteryStats() {
    log.info("Checking battery stats...");
    List<BatteryCronView> batteryData = scaleDataRepository.findBatteryData();

    batteryData.forEach(
        data -> {
          log.info("Got count {} for deviceId {}", data.getCount(), data.getDeviceid());
          if (data.getCount() > 3) {
            try {
              emailService.sendBatteryAlert(data.getCount(), data.getDeviceid());
            } catch (MessagingException | UnsupportedEncodingException e) {
              log.error(
                  "Tried to send Battery Alert for deviceId {} with count {}. {}",
                  data.getDeviceid(),
                  data.getCount(),
                  e);
            }
          }
        });
    log.info("Finished checking battery stats...");
  }
}
