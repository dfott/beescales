package de.beescales.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scale_details")
public class ScaleDetail {

  @Id
  @Column(name = "device_id")
  Long deviceId;

  @ManyToOne
  @JoinColumn(name = "scale_location_id")
  @JsonIgnoreProperties("scales")
  ScaleLocation scaleLocation;

  @OneToOne(mappedBy = "scaleDetail")
  @JsonIgnoreProperties("scaleDetail")
  ScaleThreshold scaleThreshold;

  String name;
  String color;
}
