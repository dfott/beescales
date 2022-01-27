package de.beescales.scheduled;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import java.time.Duration;

@SpringBootTest
class WeightCronIT {

  @SpyBean WeightCron scheduledTask;

  @Test
  void checkAbsoluteWeight() {
    await()
        .atMost(Duration.ofSeconds(11))
        .untilAsserted(
            () -> {
              verify(scheduledTask, atLeast(2)).checkAbsoluteWeight();
              verify(scheduledTask, atLeast(2)).checkRelativeWeight();
            });
  }
}
