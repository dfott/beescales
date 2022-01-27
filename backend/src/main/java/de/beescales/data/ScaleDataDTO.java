package de.beescales.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScaleDataDTO {

  int deviceId;
  Long locationId;
  String locationName;
  String deviceName;
  String color;
  Map<String, ScaleDataDayDTO> data;
}
