package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertEquals;
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
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.AddressNotFound;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.AvailableCases;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.NoCasesFound;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelAddressSelection;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelPostcodeSearch;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SurveyEnquiryLine;

public class CcSteps extends StepsBase {

  private String currentPage;
  private String caseNotFoundErrorMsg =
      "Confirm this is the correct address, and that the it is the address "
          + "the caller was asked to complete the survey for";

  private String addressNotFound =
      "Confirm that the postcode %s is correct. "
          + "If the postcode is incorrect, enter a corrected value, else use a different method to find the caller's case";

  @Before("@Setup")
  public void setup() throws Exception {
    super.setupForAll();
  }

  @After("@TearDown")
  public void deleteDriver() {
    super.closeDriver();
    super.destroyPubSub();
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

  @Given("The SEL operator is in CCUI find case page")
  public void theSELOperatorIsInCCUIFindCasePage() throws Exception {
    context.surveyUpdatePayload = ExampleData.createSurveyUpdate();
    context.collectionExercise = ExampleData.createCollectionExercise();
    context.caseCreatedPayload = ExampleData.createCaseUpdate(context.caseKey);
    sendInboundEvents();
    assertTrue(db.caseExists(context.caseKey));
    var st = pages.getStartPage();
    verifyCorrectOnsLogoUsed(st.getOnsLogo());

    st.clickSelLink();
    var sel = new SurveyEnquiryLine(driver);

    sel.clickFindCaseByPostcodeLink();
  }

  @And("The user selects the \"Find Case via postcode\" option")
  public void theUserSelectsTheFindCaseViaPostcodeOption() {
    var st = pages.getStartPage();
    verifyCorrectOnsLogoUsed(st.getOnsLogo());

    st.clickSelLink();
    var sel = new SurveyEnquiryLine(driver);

    sel.clickFindCaseByPostcodeLink();
  }

  @When("The user is presented with callers postcode search option page")
  public void theUserIsPresentedWithCallersPostcodeSearchOptionPage() {
    var page = new SelPostcodeSearch(driver);
    currentPage = "SelPostcodeSearch";
    assertNotNull(page);
  }

  @Then("The user enters the callers postcode")
  public void theUserEntersTheCallersPostcode() {
    var page = new SelPostcodeSearch(driver);
    page.inputPostcode("EX41EH");
  }

  @Then("The system displays a list of addresses for the postcode")
  public void theSystemDisplaysAListOfAddressesForThePostcode() {
    var page = new SelAddressSelection(driver);
    currentPage = "SelAddressSelection";
    assertEquals("43 addresses found for postcode EX4 1EH", page.getAddressList());
  }

  @And("The user selects the callers address from the list of addresses which is number {string}")
  public void theUserSelectsTheCallersAddressFromTheListOfAddressesWhichIsNumber(
      String addressNumber) {
    var page = new SelAddressSelection(driver);
    if (addressNumber.equals("6")) {
      page.selectFourthOption();
    } else if (addressNumber.equals("6A")) {
      page.selectThirdOption();
    }
  }

  @And("The user clicks \"Continue\"")
  public void theUserClicksContinue() {
    switch (currentPage) {
      case "SelPostcodeSearch":
        var page = new SelPostcodeSearch(driver);
        page.clickContinueButton();
        break;
      case "SelAddressSelection":
        var pageAdd = new SelAddressSelection(driver);
        pageAdd.clickContinueButton();
        break;
      default:
        throw new IllegalStateException(
            String.format("Failed to find the “Conitnue“ button for page %s", currentPage));
    }
  }

  @Then("The system displays the case details for the callers address")
  public void theSystemDisplaysTheCaseDetailsForTheCallersAddress() {
    var page = new AvailableCases(driver);
    assertNotNull(page);
  }

  @Then("CCSvc returns no case for the selected address")
  public void ccsvcReturnsNoCaseForTheSelectedAddress() {
    var page = new NoCasesFound(driver);
    assertNotNull(page);
  }

  @And("The user is presented with a no cases message")
  public void theUserIsPresentedWithANoCasesMessage() {
    var page = new NoCasesFound(driver);
    assertEquals(caseNotFoundErrorMsg, page.getNoCaseFoundMsg());
  }

  @And("The user selects \"I cannot find the caller's address\" from the list")
  public void theUserSelectsICannotFindTheCallersAddressFromTheList() {
    var page = new SelAddressSelection(driver);
    page.selectCannotFindAddress();
  }

  @And("The user is presented with a address not found message for postcode {string}")
  public void theUserIsPresentedWithAAddressNotFoundMessageForPostcode(String postCode) {
    var page = new AddressNotFound(driver);
    String expectedMessage = String.format(addressNotFound, postCode);
    assertEquals(expectedMessage, page.getNoAddressFoundMsg());
  }
}
