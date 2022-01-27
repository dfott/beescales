package de.beescales.dto;

import java.time.LocalDateTime;

public interface ScaleDataView {
  int getId();

  int getDeviceid();

  LocalDateTime getEpoche();

  Double getAvgWeight();

  Double getAvgTemp();

  Double getMinTemp();

  Double getMaxTemp();

  Double getAvgBatt();

  Double getCorr();

  Double getCrop();

  Double getAltitude();

  Double getLatitude();

  Double getLongitude();

  Double getWindRichtung();

  Double getWindgeschw();

  Double getRedLuftdruck();

  Double getRelLuftfeuchte();

  Double getLufttemp();
}
