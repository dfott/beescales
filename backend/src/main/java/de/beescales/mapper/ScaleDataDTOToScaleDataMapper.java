package de.beescales.mapper;

import de.beescales.data.ScaleData;
import de.beescales.data.ScaleDataDayDTO;
import de.beescales.data.ScaleDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScaleDataDTOToScaleDataMapper {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public Map<String, ScaleDataDayDTO> map(List<ScaleData> data, ScaleDetail scaleDetail) {
    HashMap<String, ScaleDataDayDTO> groupedData = new HashMap<>();
    List<ScaleData> filteredData =
        data.stream()
            .filter(d -> Objects.equals(d.getDeviceid(), scaleDetail.getDeviceId()))
            .collect(Collectors.toList());
    for (ScaleData sd : filteredData) {
      putInMap(sd, groupedData);
    }
    return groupedData;
  }

  private void constructScaleDataDay(ScaleDataDayDTO scaleDataDay) {
    List<ScaleData> data = scaleDataDay.getData();
    scaleDataDay.setBatt(data.get(0).getBatt());
    scaleDataDay.setCrop(data.get(data.size() - 1).getWeight() - data.get(0).getWeight());
    double minTemp = Double.MAX_VALUE;
    double maxTemp = Double.MIN_VALUE;
    double tempSum = 0;
    double weightSum = 0;
    for (ScaleData sd : data) {
      minTemp = Math.min(minTemp, getZeroOrNum(sd.getTemp()));
      maxTemp = Math.max(maxTemp, getZeroOrNum(sd.getTemp()));
      tempSum += getZeroOrNum(sd.getTemp());
      weightSum += getZeroOrNum(sd.getWeight());
    }
    scaleDataDay.setWeight(weightSum / data.size());
    scaleDataDay.setMinTemp(minTemp);
    scaleDataDay.setMaxTemp(maxTemp);
    scaleDataDay.setAvgTemp(tempSum / data.size());
  }

  private Double getZeroOrNum(Double num) {
    return Optional.ofNullable(num).orElseGet(() -> (double) 0);
  }

  private void putInMap(ScaleData sd, HashMap<String, ScaleDataDayDTO> map) {
    String key = constructKey(sd);
    if (map.get(key) != null) {
      map.get(key).getData().add(sd);
    } else {
      ScaleDataDayDTO dataDay = new ScaleDataDayDTO();
      dataDay.getData().add(sd);
      map.put(key, dataDay);
    }
  }

  private String constructKey(ScaleData sd) {
    if (sd.getTs() != null) {
      return formatter.format(sd.getTs());
    } else if (sd.getEpoche() != null) {
      return formatter.format(sd.getEpoche());
    }
    return "invalid";
  }
}
