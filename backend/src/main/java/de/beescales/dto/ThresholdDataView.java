package de.beescales.dto;

import java.time.LocalDateTime;

public interface ThresholdDataView {

  LocalDateTime getEpoche();

  int getDeviceid();

  double getWeight();

  int getMin();

  int getMax();
}
