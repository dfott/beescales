package de.beescales.service;

import de.beescales.data.ScaleThreshold;
import de.beescales.exception.ThresholdNotFoundException;
import de.beescales.repository.ScaleThresholdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScaleThresholdService {

  private final ScaleThresholdRepository thresholdRepository;

  public ScaleThreshold getThreshold(long deviceId) {
    return thresholdRepository
        .findByDeviceId(deviceId)
        .orElseThrow(() -> new ThresholdNotFoundException(deviceId));
  }

  public ScaleThreshold updateThreshold(long deviceId, ScaleThreshold thresholdDTO) {
    return thresholdRepository
        .findByDeviceId(deviceId)
        .map(
            threshold -> {
              threshold.setMin(thresholdDTO.getMin());
              threshold.setMax(thresholdDTO.getMax());
              threshold.setAbsolute(thresholdDTO.isAbsolute());
              threshold.setActive(thresholdDTO.isActive());
              return thresholdRepository.save(threshold);
            })
        .orElseThrow(() -> new ThresholdNotFoundException(deviceId));
  }
}
