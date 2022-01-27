package de.beescales.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ScaleDataDayDTO {

  Double weight;
  Double crop;
  Double batt;
  double minTemp;
  double avgTemp;
  double maxTemp;
  List<ScaleData> data;

  public ScaleDataDayDTO() {
    this.data = new ArrayList<>();
  }
}
