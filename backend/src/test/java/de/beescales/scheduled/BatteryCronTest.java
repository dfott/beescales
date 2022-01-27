package de.beescales.scheduled;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.beescales.repository.ScaleDataRepository;
import de.beescales.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BatteryCronTest {

  BatteryCron sut;

  @Mock ScaleDataRepository scaleDataRepository;
  @Mock EmailService emailService;

  @BeforeEach
  void setup() {
    sut = new BatteryCron(scaleDataRepository, emailService);
  }

  @Test
  void shouldSuccessfullyCallTheEmailService() throws MessagingException, UnsupportedEncodingException {
    when(scaleDataRepository.findBatteryData())
        .thenReturn(
            List.of(
                new BatteryCronTestView(1, 123),
                new BatteryCronTestView(4, 456),
                new BatteryCronTestView(2, 789)));

    sut.checkBatteryStats();

    verify(emailService).sendBatteryAlert(4, 456);
  }
}
