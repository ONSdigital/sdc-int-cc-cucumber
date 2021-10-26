package uk.gov.ons.ctp.integration.cccucumber.data.db;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representation of Collection Exercise from database table.
 *
 * <p>Implementation note: avoid Lombok Data annotation, since generated toString, equals and
 * hashcode are considered dangerous in combination with Entity annotation.
 */
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "collection_exercise")
public class CollectionExercise {
  @ToString.Include @Id private UUID id;

  @ManyToOne(optional = false)
  private Survey survey;

  @ToString.Include private String name;

  private String reference;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private int cohortSchedule;
  private int cohorts;
  private int numberOfWaves;
  private int waveLength;
}
