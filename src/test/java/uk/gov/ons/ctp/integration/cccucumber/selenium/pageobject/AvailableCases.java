package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

@Getter
public class AvailableCases extends PageObjectBase {

  public AvailableCases(WebDriver driver) {
    super(PageId.AVAILABLE_CASES, driver);
  }
}
