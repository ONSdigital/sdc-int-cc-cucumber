package uk.gov.ons.ctp.integration.cccucumber.data;

import java.time.Instant;
import java.util.Date;
import uk.gov.ons.ctp.common.event.model.CaseUpdate;
import uk.gov.ons.ctp.common.event.model.CaseUpdateSample;
import uk.gov.ons.ctp.common.event.model.CaseUpdateSampleSensitive;
import uk.gov.ons.ctp.common.event.model.CollectionExercise;
import uk.gov.ons.ctp.common.event.model.CollectionExerciseMetadata;
import uk.gov.ons.ctp.common.event.model.SurveyUpdate;

public class ExampleData {
  public static final String DEFAULT_SURVEY_ID = "0ef602b6-35b0-11ec-b2f0-4c3275913db5";
  public static final String DEFAULT_COLLEX_ID = "1f1e7466-35b0-11ec-b103-4c3275913db5";
  public static final String DEFAULT_CASE_ID = "c45de4dc-3c3b-11e9-b210-d663bd873d13";
  public static final String VALID_MOBILE_NO = "07700 900345";

  // --- model fixtures below ...

  public static CaseUpdateSample createSample() {
    CaseUpdateSample sample = new CaseUpdateSample();
    sample.setAddressLine1("6B Okehampton Road");
    sample.setAddressLine2("");
    sample.setAddressLine3("");
    sample.setTownName("Exeter");
    sample.setPostcode("EX4 1EH");
    sample.setRegion("E");
    sample.setUprn("100040226402");
    return sample;
  }

  public static CaseUpdateSampleSensitive createSampleSensitive() {
    CaseUpdateSampleSensitive sampleSensitive = new CaseUpdateSampleSensitive();
    sampleSensitive.setPhoneNumber(VALID_MOBILE_NO);
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
      CaseUpdateSample sample, CaseUpdateSampleSensitive sampleSensitive, String id) {
    CaseUpdate cc = new CaseUpdate();
    cc.setCaseId(id);
    cc.setSurveyId(DEFAULT_SURVEY_ID);
    cc.setCollectionExerciseId(DEFAULT_COLLEX_ID);
    cc.setCaseRef("10000000017");
    cc.setRefusalReceived(null);
    cc.setInvalid(false);
    cc.setSample(sample);
    cc.setSampleSensitive(sampleSensitive);
    cc.setCreatedAt(new Date());
    cc.setLastUpdatedAt(new Date());
    return cc;
  }

  public static CaseUpdate createCaseUpdate(String id) {
    CaseUpdateSample sample = createSample();
    CaseUpdateSampleSensitive sampleSensitive = createSampleSensitive();
    return createCaseUpdate(sample, sampleSensitive, id);
  }

  public static SurveyUpdate createSurveyUpdate() {
    SurveyUpdate surveyUpdate = new SurveyUpdate();
    surveyUpdate.setSurveyId(DEFAULT_SURVEY_ID);
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
    surveyUpdate.setMetadata("{\n" + "        \"ex_e4\": true\n" + "      }");
    return surveyUpdate;
  }

  public static CollectionExercise createCollectionExercise() {
    CollectionExercise collectionExercise = new CollectionExercise();
    collectionExercise.setSurveyId(DEFAULT_SURVEY_ID);
    collectionExercise.setCollectionExerciseId(DEFAULT_COLLEX_ID);
    collectionExercise.setName("CCcuc");
    collectionExercise.setStartDate(Date.from(Instant.parse("2021-09-17T23:59:59.999Z")));
    collectionExercise.setEndDate(Date.from(Instant.parse("2023-09-27T23:59:59.999Z")));
    collectionExercise.setReference("MVP012021");
    collectionExercise.setMetadata(createCollectionExerciseMetaData());
    return collectionExercise;
  }
}
