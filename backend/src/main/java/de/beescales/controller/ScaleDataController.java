package de.beescales.controller;

import de.beescales.data.ScaleDataDTO;
import de.beescales.service.ScaleDataService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ScaleDataController {

  private final ScaleDataService scaleDataService;

  @GetMapping("/data")
  public ResponseEntity<List<ScaleDataDTO>> publishScaleData(
      @RequestParam(value = "showDetailed", defaultValue = "false") boolean showDetailed,
      @RequestParam(value = "from", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime from,
      @RequestParam(value = "to", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime to,
      Principal principal) {
    CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES);
    boolean isAuthenticated = principal != null;
    List<ScaleDataDTO> response =
        showDetailed
            ? scaleDataService.getDetailedScaleData(from, to, isAuthenticated)
            : scaleDataService.getScaleData(from, to, isAuthenticated);
    return ResponseEntity.ok().cacheControl(cacheControl).body(response);
  }

  @GetMapping("/data/{deviceId}")
  public ResponseEntity<ScaleDataDTO> publishScaleDataByDeviceId(
      @PathVariable long deviceId,
      @RequestParam(value = "showDetailed", defaultValue = "false") boolean showDetailed,
      @RequestParam(value = "from", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime from,
      @RequestParam(value = "to", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime to) {
    CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES);
    ScaleDataDTO response =
        showDetailed
            ? scaleDataService.getDetailedScaleDataByDeviceId(from, to, deviceId)
            : scaleDataService.getScaleDataByDeviceId(from, to, deviceId);
    return ResponseEntity.ok().cacheControl(cacheControl).body(response);
  }
}
