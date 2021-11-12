package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.UUID;

import io.cucumber.java.en.When;
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

  @Before("@Setup")
  public void setup() throws Exception {
    super.setupForAll();
  }

  @After("@TearDown")
  public void deleteDriver() {
    super.closeDriver();
    super.destroyPubSub();
  }

  @Given("The SEL operator finds a case by postcode and a list of addresses is displayed")
  public void theSELOperatorFindsACaseByPostcodeAndAListOfAddressesIsDisplayed() throws Exception {
    context.surveyUpdatePayload = ExampleData.createSurveyUpdate();
    context.collectionExercise = ExampleData.createCollectionExercise();
    context.caseCreatedPayload = ExampleData.createCaseUpdate(context.caseKey);
    sendInboundEvents();
    var st = pages.getStartPage();
    verifyCorrectOnsLogoUsed(st.getOnsLogo());

    st.clickSelLink();
    var sel = new SurveyEnquiryLine(driver);

    sel.clickFindCaseByPostcodeLink();

    var newPage = new SelPostcodeSearch(driver);
    newPage.inputPostcode("EX41EH");
    newPage.clickContinueButton();
  }

  @When("The user selects the callers address from the list of addresses which is number {string}")
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
    var pageAdd = new SelAddressSelection(driver);
    pageAdd.clickContinueButton();
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
    assertEquals(NoCasesFound.ERROR_MSG, page.getNoCaseFoundMsg());
  }

  @When("The user selects \"I cannot find the caller's address\" from the list")
  public void theUserSelectsICannotFindTheCallersAddressFromTheList() {
    var page = new SelAddressSelection(driver);
    page.selectCannotFindAddress();
  }

  @Then("The user is presented with a address not found message for postcode {string}")
  public void theUserIsPresentedWithAAddressNotFoundMessageForPostcode(String postCode) {
    var page = new AddressNotFound(driver);
    String addressError = String.format(AddressNotFound.ERROR_MSG, postCode);
    assertEquals(addressError, page.getNoAddressFoundMsg());
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
