package de.beescales.controller;

import de.beescales.data.ScaleDetail;
import de.beescales.data.ScaleLocation;
import de.beescales.exception.DeviceNotFoundException;
import de.beescales.service.ScaleSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/settings")
public class ScaleSettingsController {

  private final ScaleSettingsService settingsService;

  @GetMapping
  public ResponseEntity<List<ScaleLocation>> getAll() {
    return ResponseEntity.ok(settingsService.getAll());
  }

  @GetMapping("/{deviceId}")
  public ResponseEntity<ScaleDetail> getDetailsByDeviceId(@PathVariable Long deviceId) {
    return ResponseEntity.ok(
        settingsService
            .getDetailById(deviceId)
            .orElseThrow(() -> new DeviceNotFoundException(deviceId)));
  }

  @PutMapping("/{deviceId}")
  public ResponseEntity<ScaleDetail> updateDetailByDeviceId(
      @PathVariable Long deviceId, @RequestBody ScaleDetail scaleDetailDto) {
    ScaleDetail res =
        settingsService
            .getDetailById(deviceId)
            .map(scaleDetail -> settingsService.updateDetail(scaleDetail, scaleDetailDto))
            .orElseThrow(() -> new DeviceNotFoundException(deviceId));
    return ResponseEntity.ok(res);
  }
}
