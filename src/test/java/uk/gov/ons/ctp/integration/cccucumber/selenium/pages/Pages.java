package uk.gov.ons.ctp.integration.cccucumber.selenium.pages;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelAddressSelection;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SelPostcodeSearch;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.StartPage;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject.SurveyEnquiryLine;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class Pages {

  @Value("${ccui.baseurl}")
  private String envBaseUrl;

  @Autowired private WebDriverFactory webDriverFactory;
  private WebDriver webDriver;

  @PostConstruct
  private void setupWebDriver() {
    this.webDriver = webDriverFactory.getWebDriver();
  }

  public StartPage getStartPage() {
    return new StartPage(webDriver, envBaseUrl);
  }

  public SurveyEnquiryLine getSurveyEnquiryLine() {
    return new SurveyEnquiryLine(webDriver);
  }

  public SelPostcodeSearch getSelPostcodeSearch() {
    return new SelPostcodeSearch(webDriver);
  }

  public SelAddressSelection getSelAddressSelection() {
    return new SelAddressSelection(webDriver);
  }

  public WebDriver getWebDriver() {
    return webDriver;
  }
}
