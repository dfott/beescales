package de.beescales.repository;

import de.beescales.dto.BatteryCronView;
import de.beescales.data.ScaleData;
import de.beescales.dto.ScaleDataView;
import de.beescales.dto.ThresholdDataView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface ScaleDataRepository extends JpaRepository<ScaleData, Long> {

  @Query(
      nativeQuery = true,
      value =
          "SELECT id,\n"
              + "deviceid,\n"
              + "IFNULL(epoche, ts) as epoche,\n"
              + "avg(weight) as avgWeight,\n"
              + "avg(temp) as avgTemp,\n"
              + "avg(batt) as avgBatt,\n"
              + "min(temp) as minTemp,\n"
              + "max(temp) as maxTemp,\n"
              + "corr,\n"
              + "(SUBSTRING_INDEX( GROUP_CONCAT(weight ORDER BY epoche DESC), ',', 1) - SUBSTRING_INDEX( GROUP_CONCAT(weight ORDER BY epoche ASC), ',', 1)) as crop,\n"
              + "altitude,\n"
              + "latitude,\n"
              + "longitude,\n"
              + "WindRichtung,\n"
              + "Windgeschw,\n"
              + "redLuftdruck,\n"
              + "relLuftfeuchte,\n"
              + "Lufttemp\n"
              + "from scaleData\n"
              + "WHERE epoche BETWEEN :from AND :to\n"
              + "AND deviceid=:deviceid\n"
              + "GROUP BY YEAR(epoche), MONTH(epoche), DAY(epoche), deviceid\n")
  List<ScaleDataView> findAverageScaleDataBetweenDatesByDeviceId(
      LocalDateTime from, LocalDateTime to, Long deviceid);

  @Query(
      nativeQuery = true,
      value =
          "SELECT id,\n"
              + "deviceid,\n"
              + "IFNULL(epoche, ts) as epoche,\n"
              + "avg(weight) as avgWeight,\n"
              + "avg(temp) as avgTemp,\n"
              + "avg(batt) as avgBatt,\n"
              + "min(temp) as minTemp,\n"
              + "max(temp) as maxTemp,\n"
              + "corr,\n"
              + "(SUBSTRING_INDEX( GROUP_CONCAT(weight ORDER BY epoche DESC), ',', 1) - SUBSTRING_INDEX( GROUP_CONCAT(weight ORDER BY epoche ASC), ',', 1)) as crop,\n"
              + "altitude,\n"
              + "latitude,\n"
              + "longitude,\n"
              + "WindRichtung,\n"
              + "Windgeschw,\n"
              + "redLuftdruck,\n"
              + "relLuftfeuchte,\n"
              + "Lufttemp\n"
              + "from scaleData\n"
              + "WHERE epoche BETWEEN :from AND :to\n"
              + "GROUP BY YEAR(epoche), MONTH(epoche), DAY(epoche), deviceid\n")
  List<ScaleDataView> findAverageScaleDataBetweenDates(LocalDateTime from, LocalDateTime to);

  @Query(
      nativeQuery = true,
      value =
          "SELECT id,\n"
              + "deviceid,\n"
              + "IFNULL(epoche, ts) as epoche,\n"
              + "weight,\n"
              + "temp,\n"
              + "batt,\n"
              + "corr,\n"
              + "crop,\n"
              + "altitude,\n"
              + "latitude,\n"
              + "longitude,\n"
              + "WindRichtung,\n"
              + "Windgeschw,\n"
              + "redLuftdruck,\n"
              + "relLuftfeuchte,\n"
              + "Lufttemp\n"
              + "from scaleData\n"
              + "WHERE epoche BETWEEN :from AND :to\n"
              + "AND deviceid=:deviceid\n")
  List<ScaleData> findDetailedScaleDataBetweenDatesByDeviceId(
      LocalDateTime from, LocalDateTime to, Long deviceid);

  @Query(
      nativeQuery = true,
      value =
          "SELECT id,\n"
              + "deviceid,\n"
              + "IFNULL(epoche, ts) as epoche,\n"
              + "weight,\n"
              + "ts,\n"
              + "temp,\n"
              + "batt,\n"
              + "corr,\n"
              + "crop,\n"
              + "altitude,\n"
              + "latitude,\n"
              + "longitude,\n"
              + "WindRichtung,\n"
              + "Windgeschw,\n"
              + "redLuftdruck,\n"
              + "relLuftfeuchte,\n"
              + "Lufttemp\n"
              + "from scaleData\n"
              + "WHERE epoche BETWEEN :from AND :to\n")
  List<ScaleData> findDetailedScaleDataBetweenDates(LocalDateTime from, LocalDateTime to);

  @Query(
      nativeQuery = true,
      value =
          "SELECT count(*) as count, deviceid\n"
              + "FROM(\n"
              + "        SELECT deviceid, batt FROM scaleData\n"
              + "        WHERE ts > NOW() - INTERVAL 20 MINUTE OR\n"
              + "                        epoche  > NOW() - INTERVAL 20 MINUTE AND\n"
              + "                        batt < 3.5\n"
              + "    ) as sDdI\n"
              + "GROUP BY deviceid;")
  List<BatteryCronView> findBatteryData();

  @Query(
          nativeQuery = true,
          value =
          "SELECT IFNULL(epoche, ts) as epoche, deviceid, weight," +
                  "scale_threshold.min, scale_threshold.max\n" +
                  " FROM scaleData\n" +
          "INNER JOIN scale_threshold\n" +
          "ON scaleData.deviceid=scale_threshold.device_id\n" +
          "AND epoche + INTERVAL 1 HOUR > NOW() - INTERVAL 20 MINUTE\n" +
          "AND (weight < scale_threshold.min OR weight > scale_threshold.max)\n" +
          "AND scale_threshold.is_absolute=1 AND scale_threshold.is_active=1;\n"
  )
  List<ThresholdDataView> findAbsoluteByThreshold();

  @Query(
          nativeQuery = true,
          value =
                  "SELECT IFNULL(epoche, ts) as epoche, deviceid, weight," +
                          "scale_threshold.min, scale_threshold.max\n" +
                          " FROM scaleData\n" +
                          "INNER JOIN scale_threshold\n" +
                          "ON scaleData.deviceid=scale_threshold.device_id\n" +
                          "AND epoche + INTERVAL 1 HOUR > NOW() - INTERVAL 20 MINUTE\n" +
                          "AND scale_threshold.is_absolute=0 AND scale_threshold.is_active=1\n" +
                          "ORDER BY epoche;"
  )
  List<ThresholdDataView> findRelativeByThreshold();
}
