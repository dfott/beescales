package de.beescales.service;

import de.beescales.dto.ThresholdDataView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

  JavaMailSender mailSender;

  public void sendRelativeWeightAlert(int deviceId, int min, int max, List<ThresholdDataView> data) throws MessagingException, UnsupportedEncodingException {
    String dataString = data
            .stream()
            .reduce("", (s, thresholdDataView) -> s + String.format("%s, %f%n", thresholdDataView.getEpoche(), thresholdDataView.getWeight()), (s, s2) -> s + s2);
    dataString = "Datum, Gewicht\n" + dataString;
    String content =
            "Für die Waage mit der deviceId " + deviceId + " wurde in den letzten 20 Minuten"
                    + " der relative Threshold über- oder unterschritten."
                    + " Somit war die Differenz des letzten und ersten in diesem Zeitraum"
                    + " gemessenen Wertes kleiner als " + min + " oder über " + max
                    + " Folgende Einträge wurden geprüft:\n" + dataString;
    log.info("Sending out message:\n{}", content);

    String subject = "Weight Alert!";
    sendMail(subject, content);
  }

  public void sendAbsoluteWeightAlert(int deviceId, int min, int max, List<ThresholdDataView> data) throws MessagingException, UnsupportedEncodingException {
    String dataString = data
            .stream()
            .reduce("", (s, thresholdDataView) -> s + String.format("%s, %f%n", thresholdDataView.getEpoche(), thresholdDataView.getWeight()), (s, s2) -> s + s2);
    dataString = "Datum, Gewicht\n" + dataString;
    String content =
            "Für die Waage mit der deviceId " + deviceId + " wurde in den letzten 20 Minuten"
                    + " der absolute Threshold über- oder unterschritten."
                    + " Somit war das gemessene Gewicht von drei oder mehr Einträgen"
                    + "  kleiner als " + min + " oder über " + max
                    + " Folgende Einträge wurden geprüft:\n" + dataString;
    log.info("Sending out message:\n{}", content);

    String subject = "Weight Alert!";
    sendMail(subject, content);
  }

  public void sendBatteryAlert(int count, int deviceId) throws MessagingException, UnsupportedEncodingException {
    log.info("Sending mail to device {} with count {}", deviceId, count);
    String subject = "Beescales: Battery Alert";
    String content =
        "Battery Alert! The battery for deviceId "
            + deviceId
            + " was below 3.5 exactly "
            + count
            + " times.";
    sendMail(subject, content);
  }

  private void sendMail(String subject, String content) throws MessagingException, UnsupportedEncodingException {

    log.info("Sending mail {}", content);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setTo("receiver@mail.de");
    helper.setFrom(new InternetAddress("info@beescales.com", "NoReply-JD"));
    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(helper.getMimeMessage());
  }
}
