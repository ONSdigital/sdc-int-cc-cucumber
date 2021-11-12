package uk.gov.ons.ctp.integration.cccucumber.selenium.pages;

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
  public static final class General {
    public static final String LOGO = "//*[text() = 'Office for National Statistics logo']";
    public static final String PAGE_TITLE = "//main//h1";
    public static final String CONTINUE_BUTTON = "//form//main//button[@type='submit']";
  }

  public static final class Start {
    public static final String SEL_LINK_TEXT = "//a[text()='Survey Enquiry Line (SEL)']";
    public static final String TEL_OPS_LINK_TEXT = "//a[text()='Telephone Operations (TO)']";
    public static final String ADMIN_LINK_TEXT = "//a[text()='Administration']";
  }

  public static final class Sel {
    public static final String FIND_CASE_BY_POSTCODE_LINK_TEXT =
        "//a[text()='Find case via postcode']";
    public static final String FIND_CASE_BY_ADDR_SEARCH_LINK_TEXT =
        "//a[text()='Find case via address text search']";
    public static final String POSTCODE_INPUT = "//main//input[@id='postcode']";
    public static final String RADIO_THIRD_ADDR = "(//form//fieldset//input)[3]";
    public static final String RADIO_FOURTH_ADDR = "(//form//fieldset//input)[4]";
    public static final String RADIO_CANNOT_FIND_ADDR = "(//form//fieldset//input)[36]";
    public static final String ADDRESS_LIST = "//main//span";
    public static final String NO_ADDRESS_FIRST_PART_FOUND_MSG = "//main//p";
    public static final String NO_ADDRESS_SECOND_PART_FOUND_MSG = "//main//p[2] ";
    public static final String NO_CASE_FOUND_MSG = "//main//p";
  }
}
