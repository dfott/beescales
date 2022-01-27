package de.beescales.exception;

public class DeviceNotFoundException extends RuntimeException{

  public DeviceNotFoundException(Long deviceId) {
    super("No device was found with id " + deviceId);
  }
}
