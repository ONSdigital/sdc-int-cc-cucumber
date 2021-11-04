package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class NoCasesFound extends PageObjectBase {

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
