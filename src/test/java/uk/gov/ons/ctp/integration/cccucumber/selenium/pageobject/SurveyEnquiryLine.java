package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class SurveyEnquiryLine extends PageObjectBase {

  @FindBy(xpath = PageXpath.Sel.FIND_CASE_BY_POSTCODE_LINK_TEXT)
  private WebElement findCaseByPostcodeLink;

  @FindBy(xpath = PageXpath.Sel.FIND_CASE_BY_ADDR_SEARCH_LINK_TEXT)
  private WebElement findCaseByAddressSearch;

  public SurveyEnquiryLine(WebDriver driver) {
    super(PageId.SURVEY_ENQUIRY_LINE, driver);
  }

  public void clickFindCaseByPostcodeLink() {
    waitForElement(findCaseByPostcodeLink, "findCaseByPostcodeLink");
    findCaseByPostcodeLink.click();
  }

  public void clickFindCaseByAddressSearchLink() {
    waitForElement(findCaseByAddressSearch, "findCaseByAddressSearch");
    findCaseByAddressSearch.click();
  }
}
