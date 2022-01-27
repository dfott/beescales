package de.beescales.service;

import de.beescales.data.ScaleData;
import de.beescales.data.ScaleDataDTO;
import de.beescales.data.ScaleDetail;
import de.beescales.data.ScaleLocation;
import de.beescales.dto.ScaleDataView;
import de.beescales.exception.DeviceNotFoundException;
import de.beescales.mapper.ScaleDataDTOToScaleDataMapper;
import de.beescales.mapper.ScaleDataViewToScaleDataDayMapper;
import de.beescales.repository.ScaleDataRepository;
import de.beescales.repository.ScaleDetailRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScaleDataService {

  ScaleDataRepository scaleDataRepository;
  ScaleDetailRepository scaleDetailRepository;
  ScaleDataDTOToScaleDataMapper mapper;
  ScaleDataViewToScaleDataDayMapper viewMapper;

  public ScaleDataDTO getScaleDataByDeviceId(LocalDateTime from, LocalDateTime to, long deviceId) {
    log.info("Trying to fetch scale data from {} to {} for device {}", from, to, deviceId);
    List<ScaleDataView> data =
        scaleDataRepository.findAverageScaleDataBetweenDatesByDeviceId(from, to, deviceId);
    return scaleDetailRepository
        .findById(deviceId)
        .map(
            scaleDetail ->
                new ScaleDataDTO(
                    Math.toIntExact(scaleDetail.getDeviceId()),
                    scaleDetail.getScaleLocation().getId(),
                    scaleDetail.getScaleLocation().getName(),
                    scaleDetail.getName(),
                    scaleDetail.getColor(),
                    viewMapper.map(data, scaleDetail.getDeviceId())))
        .orElseGet(ScaleDataDTO::new);
  }

  public List<ScaleDataDTO> getScaleData(
      LocalDateTime from, LocalDateTime to, boolean isAuthenticated) {
    log.info("Trying to fetch scale data for all devices from {} to {}", from, to);
    List<ScaleDataView> data = scaleDataRepository.findAverageScaleDataBetweenDates(from, to);
    List<ScaleDetail> scaleDetails = scaleDetailRepository.findAll();
    HashMap<Long, ScaleLocation> anonymousLocations = new HashMap<>();
    return IntStream.range(0, scaleDetails.size())
        .mapToObj(
            index -> {
              ScaleDetail scaleDetail = scaleDetails.get(index);
              if (!isAuthenticated) {
                anonymizeData(scaleDetail, anonymousLocations, index);
              }
              return new ScaleDataDTO(
                  Math.toIntExact(scaleDetail.getDeviceId()),
                  scaleDetail.getScaleLocation().getId(),
                  scaleDetail.getScaleLocation().getName(),
                  scaleDetail.getName(),
                  scaleDetail.getColor(),
                  viewMapper.map(data, scaleDetail.getDeviceId()));
            })
        .collect(Collectors.toList());
  }

  public ScaleDataDTO getDetailedScaleDataByDeviceId(
      LocalDateTime from, LocalDateTime to, long deviceId) {
    List<ScaleData> data =
        scaleDataRepository.findDetailedScaleDataBetweenDatesByDeviceId(from, to, deviceId);
    return scaleDetailRepository
        .findById(deviceId)
        .map(
            scaleDetail ->
                new ScaleDataDTO(
                    Math.toIntExact(scaleDetail.getDeviceId()),
                    scaleDetail.getScaleLocation().getId(),
                    scaleDetail.getScaleLocation().getName(),
                    scaleDetail.getName(),
                    scaleDetail.getColor(),
                    mapper.map(data, scaleDetail)))
        .orElseThrow(() -> new DeviceNotFoundException(deviceId));
  }

  public List<ScaleDataDTO> getDetailedScaleData(
      LocalDateTime from, LocalDateTime to, boolean isAuthenticated) {
    List<ScaleData> data = scaleDataRepository.findDetailedScaleDataBetweenDates(from, to);
    List<ScaleDetail> scaleDetails = scaleDetailRepository.findAll();
    HashMap<Long, ScaleLocation> anonLocationMapping = new HashMap<>();
    return IntStream.range(0, scaleDetails.size())
        .mapToObj(
            index -> {
              ScaleDetail scaleDetail = scaleDetails.get(index);
              if (!isAuthenticated) {
                anonymizeData(scaleDetail, anonLocationMapping, index);
              }
              return new ScaleDataDTO(
                  Math.toIntExact(scaleDetail.getDeviceId()),
                  scaleDetail.getScaleLocation().getId(),
                  scaleDetail.getScaleLocation().getName(),
                  scaleDetail.getName(),
                  scaleDetail.getColor(),
                  mapper.map(data, scaleDetail));
            })
        .collect(Collectors.toList());
  }

  private void anonymizeData(
      ScaleDetail scaleDetail, HashMap<Long, ScaleLocation> locations, int index) {
    scaleDetail.setName("Waage " + (index + 1));
    ScaleLocation location = scaleDetail.getScaleLocation();
    scaleDetail.setScaleLocation(
        locations.computeIfAbsent(
            location.getId(),
            id ->
                new ScaleLocation(
                    id, "Standort " + (locations.size() + 1), Collections.emptyList())));
  }
}
