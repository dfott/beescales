package de.beescales.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.beescales.data.ScaleData;
import de.beescales.data.ScaleDataDTO;
import de.beescales.data.ScaleDataDayDTO;
import de.beescales.data.ScaleDetail;
import de.beescales.data.ScaleLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ScaleDataDTOToScaleDataMapperTest {

//  private ScaleDataDTOToScaleDataMapper sut;
//
//  @BeforeEach
//  void setup() {
//    sut = new ScaleDataDTOToScaleDataMapper();
//  }
//
//  @Test
//  void shouldMap() {
//    ScaleDetail scaleDetail = getMockDetail();
//    List<ScaleData> data = getMockScaleData();
//
//    ScaleDataDTO result = sut.map(data, scaleDetail, true);
//
//    assertThat(result.getDeviceId()).isEqualTo(123L);
//    assertThat(result.getLocationId()).isEqualTo(1L);
//    assertThat(result.getLocationName()).isEqualTo("zu hause");
//    assertThat(result.getDeviceName()).isEqualTo("geraet");
//    assertThat(result.getColor()).isEqualTo("#FFFFFF");
//
//    Map<String, ScaleDataDayDTO> resultData = result.getData();
//
//    ScaleDataDayDTO scaleDataDayDTO = resultData.get("2021-10-01");
//    assertThat(scaleDataDayDTO).isNotNull();
//    assertThat(scaleDataDayDTO.getWeight()).isEqualTo(1.0);
//    assertThat(scaleDataDayDTO.getBatt()).isEqualTo(2.0);
//    assertThat(scaleDataDayDTO.getCrop()).isEqualTo(2.0);
//    assertThat(scaleDataDayDTO.getMinTemp()).isZero();
//    assertThat(scaleDataDayDTO.getAvgTemp()).isEqualTo(8.0/3);
//    assertThat(scaleDataDayDTO.getMaxTemp()).isEqualTo(7.0);
//    assertThat(scaleDataDayDTO.getData().size()).isEqualTo(3);
//
//    scaleDataDayDTO = resultData.get("2021-10-02");
//    assertThat(scaleDataDayDTO).isNotNull();
//    assertThat(scaleDataDayDTO.getData().size()).isOne();
//
//    scaleDataDayDTO = resultData.get("2021-11-01");
//    assertThat(scaleDataDayDTO).isNotNull();
//    assertThat(scaleDataDayDTO.getData().size()).isEqualTo(2);
//
//    scaleDataDayDTO = resultData.get("2022-10-01");
//    assertThat(scaleDataDayDTO).isNotNull();
//    assertThat(scaleDataDayDTO.getData().size()).isOne();
//  }
//
//  private ScaleDetail getMockDetail() {
//    return new ScaleDetail(123L, new ScaleLocation(1L, "zu hause"), "geraet", "#FFFFFF");
//  }
//
//  private List<ScaleData> getMockScaleData() {
//    return List.of(
//        constructScaleData(1.0, 2.0, 1.0, LocalDateTime.of(2021, 10, 1, 1, 1)),
//        constructScaleData(2.0, 2.0, 0, LocalDateTime.of(2021, 10, 1, 2, 1)),
//        constructScaleData(3.0, 2.0, 7.0, LocalDateTime.of(2021, 10, 1, 3, 1)),
//        constructScaleData(4.0, 2.0, 7.0, LocalDateTime.of(2021, 10, 2, 1, 1)),
//        constructScaleData(5.0, 2.0, 1.0, LocalDateTime.of(2021, 11, 1, 1, 1)),
//        constructScaleData(7.0, 2.0, 0, LocalDateTime.of(2021, 11, 1, 1, 1)),
//        constructScaleData(10.0, 2.0, 1.0, LocalDateTime.of(2022, 10, 1, 1, 1)));
//  }
//
//  private ScaleData constructScaleData(double weight, double batt, double temp, LocalDateTime ts) {
//    return ScaleData.builder().ts(ts).weight(weight).batt(batt).temp(temp).build();
//  }
}
