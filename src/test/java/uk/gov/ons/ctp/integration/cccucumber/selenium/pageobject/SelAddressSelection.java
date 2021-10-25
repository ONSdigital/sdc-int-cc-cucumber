package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

@Getter
public class SelAddressSelection extends PageObjectBase {

  @FindBy(xpath = PageXpath.Sel.RADIO_FOURTH_ADDR)
  private WebElement fourthOption;

  @FindBy(xpath = PageXpath.General.CONTINUE_BUTTON)
  private WebElement continueButton;

  public SelAddressSelection(WebDriver driver) {
    super(PageId.SEL_ADDR_SELECTION, driver);
  }

  public void selectFourthOption() {
    waitForElement(fourthOption, "fourthOption");
    fourthOption.click();
  }

  public void clickContinueButton() {
    waitForElement(continueButton, "continueButton");
    continueButton.click();
  }
}
