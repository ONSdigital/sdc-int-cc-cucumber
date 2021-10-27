package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageXpath;

@Getter
public class StartPage extends PageObjectBase {

  public StartPage(final WebDriver driver, final String urlPrefix) {
    super(PageId.START, driver, urlPrefix);
  }

  @FindBy(xpath = PageXpath.General.LOGO)
  private WebElement onsLogo;

  @FindBy(xpath = PageXpath.Start.ADMIN_LINK_TEXT)
  private WebElement adminLink;

  @FindBy(xpath = PageXpath.Start.TEL_OPS_LINK_TEXT)
  private WebElement telephoneOpsLink;

  @FindBy(xpath = PageXpath.Start.SEL_LINK_TEXT)
  private WebElement selLink;

  public void clickAdminLink() {
    waitForElement(adminLink, "adminLink");
    adminLink.click();
  }

  public void clickTelOpsLink() {
    waitForElement(telephoneOpsLink, "telephoneOpsLink");
    telephoneOpsLink.click();
  }

  public void clickSelLink() {
    waitForElement(selLink, "selLink");
    selLink.click();
  }
}
