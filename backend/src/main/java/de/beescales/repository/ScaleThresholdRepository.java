package de.beescales.repository;

import de.beescales.data.ScaleThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ScaleThresholdRepository extends JpaRepository<ScaleThreshold, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM scale_threshold WHERE device_id=:deviceId")
  Optional<ScaleThreshold> findByDeviceId(long deviceId);
}
