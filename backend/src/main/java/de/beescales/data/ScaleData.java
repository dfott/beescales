package de.beescales.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scaleData")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScaleData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  Long deviceid;
  LocalDateTime ts;
  Double weight;
  Double corr;
  Double temp;
  Double batt;
  Double crop;
  Double altitude;
  Double latitude;
  Double longitude;
  LocalDateTime epoche;

  @Column(name = "WindRichtung")
  Integer windDirection;

  @Column(name = "Windgeschw")
  Double windSpeed;

  @Column(name = "redLuftdruck")
  Double airPressure;

  @Column(name = "relLuftfeuchte")
  Integer humidity;

  @Column(name = "Lufttemp")
  Double airTemp;
}
