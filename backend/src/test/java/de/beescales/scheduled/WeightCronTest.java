package de.beescales.scheduled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.beescales.data.ScaleDetail;
import de.beescales.data.ScaleThreshold;
import de.beescales.dto.ThresholdDataView;
import de.beescales.repository.ScaleDataRepository;
import de.beescales.service.EmailService;
import de.beescales.service.ScaleThresholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WeightCronTest {

  @Mock ScaleDataRepository scaleDataRepository;
  @Mock ScaleThresholdService thresholdService;
  @Mock EmailService emailService;

  WeightCron sut;

  @BeforeEach
  void setup() {
    sut = new WeightCron(scaleDataRepository, thresholdService, emailService);
  }

  @Test
  void shouldCheckTheRelativeThresholdsAndSendMail() throws MessagingException, UnsupportedEncodingException {
    ScaleThreshold scaleThreshold = new ScaleThreshold(1, new ScaleDetail(), -3, 3, false, true);
    List<ThresholdDataView> data =
        List.of(
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T00:00:00"), 123, 10, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T05:00:00"), 123, 7, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T10:00:00"), 123, 5, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T15:00:00"), 123, 4, -3, 3));
    when(scaleDataRepository.findRelativeByThreshold()).thenReturn(data);
    when(thresholdService.getThreshold(123)).thenReturn(scaleThreshold);

    sut.checkRelativeWeight();

    verify(emailService).sendRelativeWeightAlert(123, -3, 3, data);
    assertThat(scaleThreshold.isActive()).isFalse();
  }

  @Test
  void shouldCheckTheRelativeThresholdsAndNotSendMail() {
    List<ThresholdDataView> data =
        List.of(
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T00:00:00"), 123, 10, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T05:00:00"), 123, 9, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T10:00:00"), 123, 8, -3, 3),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T15:00:00"), 123, 9, -3, 3));
    when(scaleDataRepository.findRelativeByThreshold()).thenReturn(data);

    sut.checkRelativeWeight();

    verifyNoInteractions(emailService);
  }

  @Test
  void shouldCheckTheAbsoluteThresholdsAndSendMail() throws MessagingException, UnsupportedEncodingException {
    ScaleThreshold scaleThreshold = new ScaleThreshold(1, new ScaleDetail(), 15, 20, true, true);
    List<ThresholdDataView> data =
        List.of(
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T00:00:00"), 123, 10, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T05:00:00"), 123, 3, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T10:00:00"), 123, 1, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T15:00:00"), 123, 21, 15, 20));
    when(scaleDataRepository.findAbsoluteByThreshold()).thenReturn(data);
    when(thresholdService.getThreshold(123)).thenReturn(scaleThreshold);

    sut.checkAbsoluteWeight();

    verify(emailService).sendAbsoluteWeightAlert(123, 15, 20, data);
    assertThat(scaleThreshold.isActive()).isFalse();
  }

  @Test
  void shouldCheckTheAbsoluteThresholdsAndNotSendMail() {
    List<ThresholdDataView> data =
        List.of(
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T00:00:00"), 123, 15, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T05:00:00"), 123, 16, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T10:00:00"), 123, 17, 15, 20),
            new ThresholdTestView(LocalDateTime.parse("2021-12-01T15:00:00"), 123, 21, 15, 20));
    when(scaleDataRepository.findAbsoluteByThreshold()).thenReturn(data);

    sut.checkAbsoluteWeight();

    verifyNoInteractions(emailService);
  }
}
