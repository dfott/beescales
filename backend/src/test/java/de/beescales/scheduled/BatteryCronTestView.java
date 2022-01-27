package de.beescales.scheduled;

import de.beescales.dto.BatteryCronView;

public class BatteryCronTestView implements BatteryCronView {

  private final int count;
  private final int deviceId;

  public BatteryCronTestView(int count, int deviceId) {
    this.count = count;
    this.deviceId = deviceId;
  }

  @Override
  public int getCount() {
    return this.count;
  }

  @Override
  public int getDeviceid() {
    return this.deviceId;
  }
}
