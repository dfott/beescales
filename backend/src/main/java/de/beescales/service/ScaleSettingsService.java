package de.beescales.service;

import de.beescales.data.ScaleDetail;
import de.beescales.data.ScaleLocation;
import de.beescales.repository.ScaleDetailRepository;
import de.beescales.repository.ScaleLocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScaleSettingsService {

  private final ScaleLocationRepository scaleLocationRepository;
  private final ScaleDetailRepository detailRepository;

  public List<ScaleLocation> getAll() {
    return scaleLocationRepository.findAll();
  }

  public Optional<ScaleDetail> getDetailById(Long deviceId) {
    return detailRepository.findById(deviceId);
  }

  public ScaleDetail updateDetail(ScaleDetail scaleDetail, ScaleDetail dto) {
    scaleDetail.setName(dto.getName());
    scaleDetail.setColor(dto.getColor());
    return detailRepository.save(scaleDetail);
  }
}
