package uk.gov.ons.ctp.integration.cccucumber.glue;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.ons.ctp.common.event.model.CaseUpdate;
import uk.gov.ons.ctp.common.event.model.CollectionExercise;
import uk.gov.ons.ctp.common.event.model.EqLaunchEvent;
import uk.gov.ons.ctp.common.event.model.Header;
import uk.gov.ons.ctp.common.event.model.SurveyUpdate;
import uk.gov.ons.ctp.integration.cccucumber.data.ExampleData;

@Data
@NoArgsConstructor
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class GlueContext {
  String caseCollection = "case";
  UUID caseKey = ExampleData.DEFAULT_CASE_ID;
  CaseUpdate caseCreatedPayload;
  SurveyUpdate surveyUpdatePayload;
  CollectionExercise collectionExercise;
  String fulfilmentRequestedCode;
  Header respondentAuthenticatedHeader;
  EqLaunchEvent eqLaunchedEvent;
}
