package de.beescales.scheduled;

import de.beescales.dto.ThresholdDataView;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ThresholdTestView implements ThresholdDataView {

  LocalDateTime epoche;
  int deviceid;
  double weight;
  int min;
  int max;
}
