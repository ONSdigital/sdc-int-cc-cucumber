package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class SelAddressSelection extends PageObjectBase {

  @FindBy(xpath = PageXpath.Sel.RADIO_THIRD_ADDR)
  private WebElement thirdOption;

  @FindBy(xpath = PageXpath.Sel.RADIO_FOURTH_ADDR)
  private WebElement fourthOption;

  @FindBy(xpath = PageXpath.Sel.RADIO_CANNOT_FIND_ADDR)
  private WebElement cannotFindAddressOption;

  @FindBy(xpath = PageXpath.General.CONTINUE_BUTTON)
  private WebElement continueButton;

  @FindBy(xpath = PageXpath.Sel.ADDRESS_LIST)
  private WebElement addressList;

  public SelAddressSelection(WebDriver driver) {
    super(PageId.SEL_ADDR_SELECTION, driver);
  }

  public void selectThirdOption() {
    waitForElement(thirdOption, "thirdOption");
    thirdOption.click();
  }

  public void selectFourthOption() {
    waitForElement(fourthOption, "fourthOption");
    fourthOption.click();
  }

  public void selectCannotFindAddress() {
    waitForElement(cannotFindAddressOption, "cannotFindAddressOption");
    cannotFindAddressOption.click();
  }

  public void clickContinueButton() {
    waitForElement(continueButton, "continueButton");
    continueButton.click();
  }

  public String getAddressList() {
    waitForElement(addressList, "addressList");
    return addressList.getText();
  }
}
