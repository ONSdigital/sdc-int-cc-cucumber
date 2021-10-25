package uk.gov.ons.ctp.integration.cccucumber.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.ons.ctp.common.domain.Channel;
import uk.gov.ons.ctp.common.domain.Source;
import uk.gov.ons.ctp.common.event.TopicType;
import uk.gov.ons.ctp.integration.cccucumber.data.ExampleData;
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

  @Given("Pseudo given")
  public void dummyGiven() {
    System.out.println("GIVEN");
  }

  @When("Pseudo when")
  public void dummyWhen() {}

  @When("Pseudo and")
  public void dummyAnd() {}

  @When("Pseudo then")
  public void dummyThen() {
    System.out.println("THEN");
  }

  @Given("A case exists with my address")
  public void createKnownCase() throws Exception {
    context.surveyUpdatePayload = ExampleData.createSurveyUpdate();
    context.collectionExercise = ExampleData.createCollectionExercise();
    context.caseCreatedPayload = ExampleData.createCaseUpdate(context.caseKey);
    sendInboundEvents();
    // TODO need some checking code against the DB.
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
    // WRITEME
  }

  private void sendInboundEvents() throws Exception {
    pubSub.sendEvent(
        TopicType.SURVEY_UPDATE, Source.SAMPLE_LOADER, Channel.RM, context.surveyUpdatePayload);
    Thread.sleep(1000); // FIXME need something better.
    pubSub.sendEvent(
        TopicType.COLLECTION_EXERCISE_UPDATE,
        Source.SAMPLE_LOADER,
        Channel.RM,
        context.collectionExercise);
    Thread.sleep(1000);
    pubSub.sendEvent(
        TopicType.CASE_UPDATE, Source.CASE_SERVICE, Channel.RM, context.caseCreatedPayload);
    Thread.sleep(1000);
  }
}
