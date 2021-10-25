package uk.gov.ons.ctp.integration.cccucumber.selenium.pageobject;

/**
 * This file holds xpath expressions which are used to find common items in the CCUI web pages. An
 * introductory reference for xpath is:
 * https://blog.scrapinghub.com/2016/10/27/an-introduction-to-xpath-with-examples
 *
 * <p>The following xpath expressions aim to uniquely identify page elements yet retain enough
 * flexibility so that they don't require updating for small page changes, such as say adding in an
 * extra div layer or moving some unrelated content to an earlier part of the page.
 *
 * <p>The expressions also try to be readable in the own right. For example the following fragile
 * xpath identifies the full path to an element
 * '/html/body/div/div/form/div[2]/div/div/main/p[4]/a', can be replaced with the more readable
 * expression: '//form//main//a[@href='/webchat']'
 */
public final class PageXpath {
  public final class General {
    static final String LOGO = "//*[text() = 'Office for National Statistics logo']";
    static final String PAGE_TITLE = "//main//h1";
    static final String CONTINUE_BUTTON = "//form//main//button[@type='submit']";
  }

  static final String XPATH_PARAGRAPH_ADDRESS = "//main//h1/following-sibling::p";

  // For the Address Confirmation page
  static final String XPATH_EM_ADDRESS = "//form//p[@class='cc-address-display']";
  static final String XPATH_RADIO_ADDRESS_YES = "//fieldset//input[@id='yes']";
  static final String XPATH_RADIO_ADDRESS_NO = "//fieldset//input[@id='no']";

  public final class Start {
    static final String SEL_LINK_TEXT = "Survey Enquiry Line (SEL)";
    static final String TEL_OPS_LINK_TEXT = "Telephone Operations (TO)";
    static final String ADMIN_LINK_TEXT = "Administration";
  }
}
