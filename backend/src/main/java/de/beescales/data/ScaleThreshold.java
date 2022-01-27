package de.beescales.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scale_threshold")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScaleThreshold {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @OneToOne
  @JoinColumn(name = "device_id", referencedColumnName = "device_id")
  ScaleDetail scaleDetail;

  int min;
  int max;

  @Column(name = "is_absolute")
  boolean isAbsolute;

  @Column(name = "is_active")
  boolean isActive;
}
