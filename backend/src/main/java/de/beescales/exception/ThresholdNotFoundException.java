package de.beescales.exception;

public class ThresholdNotFoundException extends RuntimeException{

  public ThresholdNotFoundException(Long deviceId) {
    super("No threshold was found for device " + deviceId);
  }
}
