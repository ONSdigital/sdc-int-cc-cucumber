package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class AddressNotFound extends PageObjectBase {

  public static final String addressNotFound =
      "Confirm that the postcode %s is correct. "
          + "If the postcode is incorrect, enter a corrected value, else use a different method to find the caller's case";


  @FindBy(xpath = PageXpath.Sel.NO_ADDRESS_FIRST_PART_FOUND_MSG)
  private WebElement noAddressFoundFirstPartMsg;

  @FindBy(xpath = PageXpath.Sel.NO_ADDRESS_SECOND_PART_FOUND_MSG)
  private WebElement noAddressFoundSecondPartMsg;

  public AddressNotFound(WebDriver driver) {
    super(PageId.ADDRESS_NOT_FOUND, driver);
  }

  public String getNoAddressFoundMsg() {
    waitForElement(noAddressFoundFirstPartMsg, "noAddressFoundMsg");
    waitForElement(noAddressFoundSecondPartMsg, "noAddressFoundMsg");
    return noAddressFoundFirstPartMsg.getText() + " " + noAddressFoundSecondPartMsg.getText();
  }
}
