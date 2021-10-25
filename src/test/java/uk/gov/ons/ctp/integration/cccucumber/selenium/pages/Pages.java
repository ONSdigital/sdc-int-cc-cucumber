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
  private SelPostcodeSearch selPostcodeSearch;
  private SelAddressSelection selAddressSelection;

  @PostConstruct
  private void setupWebDriver() {
    this.webDriver = webDriverFactory.getWebDriver();
    this.pageTracker = new PageTracker(webDriver);
  }

  public StartPage getStartPage() {
    if (startPage == null) {
      startPage = new StartPage(webDriver, envBaseUrl);
    }
    pageTracker.verifyCurrentPage(PageId.START);
    return startPage;
  }

  public SurveyEnquiryLine getSurveyEnquiryLine() {
    SurveyEnquiryLine page = new SurveyEnquiryLine(webDriver);
    pageTracker.verifyCurrentPage(PageId.SURVEY_ENQUIRY_LINE);
    return page;
  }

  public SelPostcodeSearch getSelPostcodeSearch() {
    if (selPostcodeSearch == null) {
      selPostcodeSearch = new SelPostcodeSearch(webDriver);
    }
    pageTracker.verifyCurrentPage(PageId.SEL_POSTCODE_SEARCH);
    return selPostcodeSearch;
  }

  public SelAddressSelection getSelAddressSelection() {
    if (selAddressSelection == null) {
      selAddressSelection = new SelAddressSelection(webDriver);
    }
    pageTracker.verifyCurrentPage(PageId.SEL_ADDR_SELECTION);
    return selAddressSelection;
  }

  public WebDriver getWebDriver() {
    return webDriver;
  }
}
