package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class NoCasesFound extends PageObjectBase {

  public static final String caseNotFoundErrorMsg =
      "Confirm this is the correct address, and that the it is the address "
          + "the caller was asked to complete the survey for";

  @FindBy(xpath = PageXpath.Sel.NO_CASE_FOUND_MSG)
  private WebElement noCaseFoundMsg;

  public NoCasesFound(WebDriver driver) {
    super(PageId.NO_CASE_FOUND, driver);
  }

  public String getNoCaseFoundMsg() {
    waitForElement(noCaseFoundMsg, "errorMessage");
    return noCaseFoundMsg.getText();
  }
}
