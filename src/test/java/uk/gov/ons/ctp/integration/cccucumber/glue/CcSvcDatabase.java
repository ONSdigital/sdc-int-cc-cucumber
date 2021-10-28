package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.fail;

import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CcSvcDatabase {
  private static final int POLL_MILLIS = 100;
  private static final int POLL_MAX = 20;
  private JdbcTemplate jdbcTemplate;

  public CcSvcDatabase(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private boolean itemExists(String table, UUID id) {
    String sql = "SELECT COUNT(*) FROM " + table + " WHERE id = ? LIMIT 1";
    return jdbcTemplate.queryForObject(sql, Integer.class, id) != 0;
  }

  boolean surveyExists(UUID id) {
    return itemExists("survey", id);
  }

  boolean collectionExerciseExists(UUID id) {
    return itemExists("collection_exercise", id);
  }

  boolean caseExists(UUID id) {
    return itemExists("collection_case", id);
  }

  int deleteSurvey(UUID id) {
    String sql = "DELETE FROM survey WHERE id = ?";
    return jdbcTemplate.update(sql, id);
  }

  int deleteCollectionExercisesInSurvey(UUID surveyId) {
    String sql = "DELETE FROM collection_exercise WHERE survey_id = ?";
    return jdbcTemplate.update(sql, surveyId);
  }

  int deleteCasesInSurvey(UUID surveyId) {
    String sql =
        "DELETE FROM collection_case WHERE collection_exercise_id IN "
            + "(SELECT id FROM collection_exercise WHERE survey_id = ?)";
    return jdbcTemplate.update(sql, surveyId);
  }

  void deleteSurveyCascade(UUID surveyId) {
    deleteCasesInSurvey(surveyId);
    deleteCollectionExercisesInSurvey(surveyId);
    deleteSurvey(surveyId);
  }

  void waitForCreation(Supplier<Boolean> itemExists, String description) {
    for (int i = 0; i < POLL_MAX; i++) {
      try {
        Thread.sleep(POLL_MILLIS);
      } catch (InterruptedException e) {
        fail("Waiting interrupted");
      }
      if (itemExists.get()) {
        return;
      }
    }
    fail("Timeout waiting for creation of database object: " + description);
  }

  void waitForSurvey(UUID id) {
    waitForCreation(() -> surveyExists(id), "survey");
  }

  void waitForCollEx(UUID id) {
    waitForCreation(() -> collectionExerciseExists(id), "collection exercise");
  }

  void waitForCase(UUID id) {
    waitForCreation(() -> caseExists(id), "case");
  }
}
