package de.beescales.controller;

import de.beescales.data.ScaleThreshold;
import de.beescales.service.ScaleThresholdService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/threshold")
@AllArgsConstructor
public class ScaleThresholdController {

  private final ScaleThresholdService thresholdService;

  @GetMapping("/{deviceId}")
  public ResponseEntity<ScaleThreshold> getThreshold(@PathVariable long deviceId) {
    return ResponseEntity.ok(thresholdService.getThreshold(deviceId));
  }

  @PutMapping("/{deviceId}")
  public ResponseEntity<ScaleThreshold> updateThreshold(@PathVariable long deviceId, @RequestBody ScaleThreshold thresholdDTO) {
    return ResponseEntity.ok(thresholdService.updateThreshold(deviceId, thresholdDTO));
  }

}
