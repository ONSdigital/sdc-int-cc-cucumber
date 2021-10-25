package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

@Getter
public class SelPostcodeSearch extends PageObjectBase {

  @FindBy(xpath = PageXpath.Sel.POSTCODE_INPUT)
  private WebElement postcodeInput;

  @FindBy(xpath = PageXpath.General.CONTINUE_BUTTON)
  private WebElement continueButton;

  public SelPostcodeSearch(WebDriver driver) {
    super(PageId.SEL_POSTCODE_SEARCH, driver);
  }

  public void inputPostcode(String postcode) {
    waitForElement(postcodeInput, "postcodeInput");
    postcodeInput.sendKeys(postcode);
  }

  public void clickContinueButton() {
    waitForElement(continueButton, "continueButton");
    continueButton.click();
  }
}
