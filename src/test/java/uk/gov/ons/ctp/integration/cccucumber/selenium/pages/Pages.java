package uk.gov.ons.ctp.integration.cccucumber.selenium.pages;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.ConfirmAddress;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.StartPage;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.PageTracker.PageId;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class Pages {

  @Value("${ccui.baseurl}")
  private String envBaseUrl;

  @Autowired private WebDriverFactory webDriverFactory;
  private WebDriver webDriver;
  private PageTracker pageTracker;

  private StartPage startPage;
  private ConfirmAddress confirmAddress;

  @PostConstruct
  private void setupWebDriver() {
    this.webDriver = webDriverFactory.getWebDriver();
    this.pageTracker = new PageTracker(webDriver);
  }

  public ConfirmAddress getConfirmAddress() {
    if (confirmAddress == null) {
      confirmAddress = new ConfirmAddress(webDriver);
    }
    pageTracker.verifyCurrentPage(PageId.CONFIRM_ADDRESS);
    return confirmAddress;
  }

  public StartPage getStartPage() {
    if (startPage == null) {
      startPage = new StartPage(webDriver, envBaseUrl);
    }
    pageTracker.verifyCurrentPage(PageId.START_PAGE);
    return startPage;
  }

  public WebDriver getWebDriver() {
    return webDriver;
  }
}
