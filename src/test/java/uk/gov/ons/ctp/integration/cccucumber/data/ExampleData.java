package uk.gov.ons.ctp.integration.cccucumber.data;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import uk.gov.ons.ctp.common.event.model.CaseUpdate;
import uk.gov.ons.ctp.common.event.model.CollectionExercise;
import uk.gov.ons.ctp.common.event.model.CollectionExerciseMetadata;
import uk.gov.ons.ctp.common.event.model.SurveyUpdate;

public class ExampleData {
  public static final UUID DEFAULT_SURVEY_ID =
      UUID.fromString("0ef602b6-35b0-11ec-b2f0-4c3275913db5");
  public static final UUID DEFAULT_COLLEX_ID =
      UUID.fromString("1f1e7466-35b0-11ec-b103-4c3275913db5");
  public static final UUID DEFAULT_CASE_ID =
      UUID.fromString("89e9d998-430c-11ec-a825-4c3275913db5");
  public static final String VALID_MOBILE_NO = "07700 900345";

  // --- model fixtures below ...

  public static Map<String, String> createSample() {
    Map<String, String> sample = new HashMap<>();
    sample.put(CaseUpdate.ATTRIBUTE_ADDRESS_LINE_1, "6B Okehampton Road");
    sample.put(CaseUpdate.ATTRIBUTE_ADDRESS_LINE_2, "");
    sample.put(CaseUpdate.ATTRIBUTE_ADDRESS_LINE_3, "");
    sample.put(CaseUpdate.ATTRIBUTE_TOWN_NAME, "Exeter");
    sample.put(CaseUpdate.ATTRIBUTE_POSTCODE, "EX4 1EH");
    sample.put(CaseUpdate.ATTRIBUTE_REGION, "E");
    sample.put(CaseUpdate.ATTRIBUTE_UPRN, "100040226402");

    sample.put(CaseUpdate.ATTRIBUTE_GOR9D, "E12000009");
    sample.put(CaseUpdate.ATTRIBUTE_LA_CODE, "EX");
    sample.put(CaseUpdate.ATTRIBUTE_UPRN_LATITUDE, "50.72116");
    sample.put(CaseUpdate.ATTRIBUTE_UPRN_LONGITUDE, "-3.53363");

    sample.put(CaseUpdate.ATTRIBUTE_QUESTIONNAIRE, "12345");
    sample.put(CaseUpdate.ATTRIBUTE_SAMPLE_UNIT_REF, "REF-4321");
    sample.put(CaseUpdate.ATTRIBUTE_COHORT, "CC3");
    return sample;
  }

  public static Map<String, String> createSampleSensitive() {
    Map<String, String> sampleSensitive = new HashMap<>();
    sampleSensitive.put("phoneNumber", VALID_MOBILE_NO);
    return sampleSensitive;
  }

  public static CollectionExerciseMetadata createCollectionExerciseMetaData() {
    CollectionExerciseMetadata collectionExerciseMetadata = new CollectionExerciseMetadata();
    collectionExerciseMetadata.setCohorts(1);
    collectionExerciseMetadata.setCohortSchedule(1);
    collectionExerciseMetadata.setNumberOfWaves(1);
    collectionExerciseMetadata.setWaveLength(1);
    return collectionExerciseMetadata;
  }

  public static CaseUpdate createCaseUpdate(
      Map<String, String> sample, Map<String, String> sampleSensitive, UUID id) {
    CaseUpdate cc = new CaseUpdate();
    cc.setCaseId(id.toString());
    cc.setSurveyId(DEFAULT_SURVEY_ID.toString());
    cc.setCollectionExerciseId(DEFAULT_COLLEX_ID.toString());
    cc.setCaseRef("10000000892");
    cc.setRefusalReceived(null);
    cc.setInvalid(false);
    cc.setSample(sample);
    cc.setSampleSensitive(sampleSensitive);
    cc.setCreatedAt(new Date());
    cc.setLastUpdatedAt(new Date());
    return cc;
  }

  public static CaseUpdate createCaseUpdate(UUID id) {
    Map<String, String> sample = createSample();
    Map<String, String> sampleSensitive = createSampleSensitive();
    return createCaseUpdate(sample, sampleSensitive, id);
  }

  public static SurveyUpdate createSurveyUpdate() {
    SurveyUpdate surveyUpdate = new SurveyUpdate();
    surveyUpdate.setSurveyId(DEFAULT_SURVEY_ID.toString());
    surveyUpdate.setName("CCC");
    surveyUpdate.setSampleDefinitionUrl("test/social.json");
    surveyUpdate.setSampleDefinition(
        "[\n"
            + "      {\n"
            + "        \"columnName\": \"addressLine1\",\n"
            + "        \"rules\": [\n"
            + "          {\n"
            + "            \"className\": \"uk.gov.ons.ssdc.common.validation.MandatoryRule\"\n"
            + "          },\n"
            + "          {\n"
            + "            \"className\": \"uk.gov.ons.ssdc.common.validation.LengthRule\",\n"
            + "            \"maxLength\": 60\n"
            + "          }\n"
            + "        ]\n"
            + "      }]");
    surveyUpdate.setMetadata(Map.of("ex_e4", "true"));
    return surveyUpdate;
  }

  public static CollectionExercise createCollectionExercise() {
    CollectionExercise collectionExercise = new CollectionExercise();
    collectionExercise.setSurveyId(DEFAULT_SURVEY_ID.toString());
    collectionExercise.setCollectionExerciseId(DEFAULT_COLLEX_ID.toString());
    collectionExercise.setName("CCcuc");
    collectionExercise.setStartDate(Date.from(Instant.parse("2021-09-17T23:59:59.999Z")));
    collectionExercise.setEndDate(Date.from(Instant.parse("2023-09-27T23:59:59.999Z")));
    collectionExercise.setReference("MVP012021");
    collectionExercise.setMetadata(createCollectionExerciseMetaData());
    return collectionExercise;
  }
}
