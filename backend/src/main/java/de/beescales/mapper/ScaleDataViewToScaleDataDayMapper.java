package de.beescales.mapper;

import de.beescales.data.ScaleDataDayDTO;
import de.beescales.dto.ScaleDataView;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScaleDataViewToScaleDataDayMapper {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public Map<String, ScaleDataDayDTO> map(List<ScaleDataView> data, long deviceId) {
    return data.stream()
        .filter(dd -> dd.getDeviceid() == deviceId)
        .collect(
            Collectors.toMap(
                dataDay -> formatter.format(dataDay.getEpoche()),
                dd ->
                    new ScaleDataDayDTO(
                        dd.getAvgWeight(),
                        dd.getCrop(),
                        dd.getAvgBatt(),
                        dd.getMinTemp(),
                        dd.getAvgTemp(),
                        dd.getMaxTemp(),
                        Collections.emptyList())));
  }
}
