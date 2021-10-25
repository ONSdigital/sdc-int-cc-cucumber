package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uk.gov.ons.ctp.common.util.Wait;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

public abstract class PageObjectBase {
  protected WebDriver driver;
  private Wait wait;
  protected String classPrefix;
  protected String startURL;

  public PageObjectBase(PageId pageId, WebDriver driver) {
    this.classPrefix = pageId.name() + ":";
    this.driver = driver;
    wait = new Wait(driver);
    waitForLoading();
    PageFactory.initElements(driver, this);
  }

  protected void waitForElement(final WebElement element, final String identifier) {
    wait.forElementToBeDisplayed(5, element, classPrefix + identifier);
  }

  protected void waitForElement(
      final int timeout, final WebElement element, final String identifier) {
    wait.forElementToBeDisplayed(timeout, element, classPrefix + identifier);
  }

  protected void waitForLoading() {
    wait.forLoading();
  }
}
