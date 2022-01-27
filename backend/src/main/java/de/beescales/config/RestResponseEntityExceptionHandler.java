package de.beescales.config;

import de.beescales.exception.DeviceNotFoundException;
import de.beescales.exception.ThresholdNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {DeviceNotFoundException.class, ThresholdNotFoundException.class})
  protected void handleNotFoundException(HttpServletResponse response, RuntimeException e)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    response.setContentType("application/json");
    String errorMsg = String.format("{\"error\": \"%s\"}", e.getMessage());
    response.getWriter().println(errorMsg);
    response.getWriter().flush();
  }
}
