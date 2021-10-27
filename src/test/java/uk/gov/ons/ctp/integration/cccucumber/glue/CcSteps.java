package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.UUID;
import uk.gov.ons.ctp.common.domain.Channel;
import uk.gov.ons.ctp.common.domain.Source;
import uk.gov.ons.ctp.common.event.TopicType;
import uk.gov.ons.ctp.integration.cccucumber.data.ExampleData;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.AvailableCases;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelAddressSelection;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelPostcodeSearch;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.StartPage;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SurveyEnquiryLine;

public class CcSteps extends StepsBase {

  @Before("@Setup")
  public void setup() throws Exception {
    super.setupForAll();
  }

  @After("@TearDown")
  public void deleteDriver() {
    super.closeDriver();
    super.destroyPubSub();
  }

  @Given("A case exists with my address")
  public void createKnownCase() throws Exception {
    context.surveyUpdatePayload = ExampleData.createSurveyUpdate();
    context.collectionExercise = ExampleData.createCollectionExercise();
    context.caseCreatedPayload = ExampleData.createCaseUpdate(context.caseKey);
    sendInboundEvents();
    assertTrue(db.caseExists(context.caseKey));
  }

  @And("I have navigated to the SEL Postcode search page")
  public void navigateToSelPostcodeSeachPage() {
    StartPage st = pages.getStartPage();
    verifyCorrectOnsLogoUsed(st.getOnsLogo());

    st.clickSelLink();
    SurveyEnquiryLine sel = pages.getSurveyEnquiryLine();

    sel.clickFindCaseByPostcodeLink();
    pages.getSelPostcodeSearch();
  }

  @When("I have entered a UK postcode and clicked Continue")
  public void enteredCallersPostcode() {
    SelPostcodeSearch page = pages.getSelPostcodeSearch();
    page.inputPostcode("EX41EH");
    page.clickContinueButton();
  }

  @And("I select the desired address from a list")
  public void selectAddressFromList() {
    SelAddressSelection page = pages.getSelAddressSelection();
    page.selectFourthOption();
    page.clickContinueButton();
  }

  @Then("The case is displayed for selection")
  public void caseIsDisplayedForSelection() {
    var page = new AvailableCases(driver);
    assertNotNull(page);
  }

  private void sendInboundEvents() throws Exception {
    pubSub.sendEvent(
        TopicType.SURVEY_UPDATE, Source.SAMPLE_LOADER, Channel.RM, context.surveyUpdatePayload);
    db.waitForSurvey(UUID.fromString(context.surveyUpdatePayload.getSurveyId()));

    pubSub.sendEvent(
        TopicType.COLLECTION_EXERCISE_UPDATE,
        Source.SAMPLE_LOADER,
        Channel.RM,
        context.collectionExercise);
    db.waitForCollEx(UUID.fromString(context.collectionExercise.getCollectionExerciseId()));

    pubSub.sendEvent(
        TopicType.CASE_UPDATE, Source.CASE_SERVICE, Channel.RM, context.caseCreatedPayload);
    db.waitForCase(UUID.fromString(context.caseCreatedPayload.getCaseId()));
  }
}