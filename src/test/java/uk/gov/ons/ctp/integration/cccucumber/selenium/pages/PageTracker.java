package uk.gov.ons.ctp.integration.cccucumber.selenium.pages;

import java.io.FileOutputStream;
import java.io.IOException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class PageTracker {

  @Getter
  public enum PageId {
    START("Contact Centre", "h1"),
    SURVEY_ENQUIRY_LINE("Survey Enquiry Line", "h1"),
    SEL_POSTCODE_SEARCH("What is the caller's postcode?", "h1"),
    SEL_ADDR_SELECTION("Select the callers address", "h1"),
    AVAILABLE_CASES("Available cases", "h2");

    private String id;
    private String tag;

    private PageId(String id, String tag) {
      this.id = id;
      this.tag = tag;
    }

    String getId() {
      return id;
    }

    String getTag() {
      return tag;
    }

    String htmlFragment() {
      // don't use the start tag, since this can contain other attributes
      return id + "</" + tag + ">";
    }
  }

  private WebDriver webDriver;
  private static int pageCaptureNum = 1;

  public PageTracker(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  /**
   * Verifies that we are on the expected page.
   *
   * <p>If 'dumpPageContent' property is set to 'true' then the page content is written to a temp
   * file.
   *
   * @param expectedPage is the Page that the cucumber test code thinks we should be on.
   */
  public void verifyCurrentPage(PageId expectedPage) {
    String pageContent = getPageContent();

    // Identify current page based on its content
    PageId actualPage = null;
    for (PageId currPageId : PageId.values()) {
      if (pageContent.contains(currPageId.htmlFragment())) {
        actualPage = currPageId;
        break;
      }
    }

    // Check if we are on the expected page
    String exceptionText = null;
    if (actualPage == null) {
      String tag = expectedPage.getTag();
      String pageTitle = StringUtils.substringsBetween(pageContent, "<" + tag, "</" + tag + ">")[0];
      exceptionText =
          "Failed to identify page. Unique Page text: '" + pageTitle + "' in tag: " + tag;
    } else if (expectedPage != actualPage) {
      exceptionText =
          "On wrong page. Expected page: "
              + expectedPage.name()
              + " ActualPage: "
              + actualPage.name();
    }

    // Optionally dump page content to file
    String pageContentFile = null;
    String pageDumpingFlag = System.getProperty("dumpPageContent", "false");
    if (pageDumpingFlag.equalsIgnoreCase("true")) {
      pageContentFile = dumpPageContent(pageContent, expectedPage);
    }

    // Fail if something has gone wrong
    if (exceptionText != null) {
      if (pageContentFile == null) {
        throw new IllegalStateException(exceptionText);
      } else {
        throw new IllegalStateException(exceptionText + " PageContent: " + pageContentFile);
      }
    }
  }

  private String getPageContent() {
    final Document doc = Jsoup.parse(webDriver.getPageSource());
    return doc.html();
  }

  private String dumpPageContent(String pageContent, PageId expectedPage) {
    String fileName = "/tmp/cc-cuc." + (pageCaptureNum++) + "." + expectedPage.name() + ".html";
    try {
      FileOutputStream outputStream = new FileOutputStream(fileName);
      outputStream.write(pageContent.getBytes());
      outputStream.close();

      // Need to tell the user where the debug file can be found.
      // Using stdout as logging doesn't appear.
      System.out.println("Page content saved to: " + fileName);

      return fileName;
    } catch (IOException e) {
      throw new IllegalStateException("Failed to dump page content to: " + fileName, e);
    }
  }
}
