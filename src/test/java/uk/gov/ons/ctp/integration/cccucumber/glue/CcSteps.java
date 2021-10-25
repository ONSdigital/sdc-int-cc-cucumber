package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriverException;
import uk.gov.ons.ctp.common.event.TopicType;
import uk.gov.ons.ctp.common.util.Wait;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.ConfirmAddress;

public class CcSteps extends StepsBase {
  private Wait wait;

  @Before("@Setup")
  public void setup() throws Exception {
    super.setupForAll();
    wait = new Wait(driver);
    pages.getStartPage();
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

  @Given("The respondent confirms a valid {} address {string}")
  public void confirmAddress(final String postCode) throws Exception {
    //  StartPage startPage = pages.getStartPage();
    pages.getStartPage();
    // startPage.clickRequestNewCodeLink();
    confirmYourAddress(postCode);
  }

  @Then("I am presented with a page to confirm my address")
  public void verifyConfirmMyAddress() {
    ConfirmAddress confirmAddress = pages.getConfirmAddress();
    verifyCorrectOnsLogoUsed(confirmAddress.getOnsLogo());

    assertEquals(
        "address confirmation title has incorrect text",
        pages.getConfirmAddress().getExpectedConfirmText(),
        pages.getConfirmAddress().getConfirmAddressTitleText());
  }

  @Given("I select the “Yes, this address is correct” option")
  public void confirmAddressIsCorrect() {
    pages.getConfirmAddress().clickOptionYes();
  }

  @Given("I click the “Continue” button")
  public void clickContinueAfterConfirmAddress() {
    wait.forLoading(1);
    try {
      pages.getConfirmAddress().clickContinueButton();
    } catch (WebDriverException e) {
      // tolerate no EQ deployment for testing
      // context.errorMessageContainingCallToEQ = e.getMessage();
    }
  }

  @Given("an empty queue exists for sending Respondent Authenticated events")
  public void emptyEventQueueForRespondentAuthenticated() throws Exception {
    emptyEventQueue(TopicType.UAC_AUTHENTICATE);
  }

  @Given("an empty queue exists for sending Survey Launched events")
  public void emptyEventQueuForSurveyLaunched() throws Exception {
    emptyEventQueue(TopicType.SURVEY_LAUNCH);
  }

  @Given("an empty queue exists for sending Fulfilment Requested events")
  public void emptyEventQueueForFulfilmentRequested() throws Exception {
    emptyEventQueue(TopicType.FULFILMENT);
  }

  //  private void sendRequiredInboundEvents(TopicType eventType) throws Exception {
  //    pubSub.sendEvent(
  //        TopicType.SURVEY_UPDATE, Source.SAMPLE_LOADER, Channel.RM, context.surveyUpdatePayload);
  //    pubSub.sendEvent(
  //        TopicType.COLLECTION_EXERCISE_UPDATE,
  //        Source.SAMPLE_LOADER,
  //        Channel.RM,
  //        context.collectionExercise);
  //    pubSub.sendEvent(
  //        TopicType.CASE_UPDATE, Source.CASE_SERVICE, Channel.RM, context.caseCreatedPayload);
  //  }

  private void confirmYourAddress(String postCode) throws Exception {
    // TODO we will revisit these when the features are made to work, when Address typeahead is
    // working in cucumber tests
    //    emptyEventQueue(EventType.NEW_ADDRESS_REPORTED);
    emptyEventQueue(TopicType.FULFILMENT);

    ConfirmAddress confirmAddressPage = pages.getConfirmAddress();
    confirmAddressPage.clickOptionYes();
    confirmAddressPage.clickContinueButton();
  }
}
