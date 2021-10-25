package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

@Getter
public class ConfirmAddress extends PageObjectBase {

  public ConfirmAddress(WebDriver driver) {
    super(PageId.CONFIRM_ADDRESS, driver);
  }

  @FindBy(xpath = PageXpath.General.LOGO)
  private WebElement onsLogo;

  @FindBy(xpath = PageXpath.General.PAGE_TITLE)
  private WebElement confirmAddressTitle;

  @FindBy(xpath = PageXpath.XPATH_PARAGRAPH_ADDRESS)
  private WebElement wholeAddressParagraph;

  private String firstLineAddress;
  private String secondLineAddress;
  private String thirdLineAddress;
  private String townName;
  private String postcode;

  @FindBy(xpath = PageXpath.XPATH_RADIO_ADDRESS_YES)
  private WebElement optionYes;

  @FindBy(xpath = PageXpath.XPATH_RADIO_ADDRESS_NO)
  private WebElement optionNo;

  @FindBy(xpath = PageXpath.General.CONTINUE_BUTTON)
  private WebElement continueButton;

  public WebElement getOnsLogo() {
    waitForElement(onsLogo, "onsLogo");
    return onsLogo;
  }

  public String getConfirmAddressTitleText() {
    waitForElement(confirmAddressTitle, "confirmAddressTitle");
    return confirmAddressTitle.getText();
  }

  public void clickContinueButton() {
    waitForElement(continueButton, "continueButton");
    continueButton.click();
  }

  public void clickOptionYes() {
    waitForElement(optionYes, "optionYes");
    optionYes.click();
  }

  public void clickOptionNo() {
    waitForElement(optionNo, "optionNo");
    optionNo.click();
  }

  public void setAddressTextFields() {
    waitForElement(wholeAddressParagraph, "wholeAddressParagraph");
    String address = wholeAddressParagraph.getText();
    String[] addressText = address.split("\n");
    firstLineAddress = addressText[0];
    secondLineAddress = addressText[1];
    thirdLineAddress = addressText[2];
    townName = addressText[3];
    postcode = addressText[4];
  }

  public String getExpectedConfirmText() {
    return "Is this the correct address?";
  }
}
