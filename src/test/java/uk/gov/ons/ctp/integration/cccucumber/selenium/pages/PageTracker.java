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
    START_PAGE("Contact Centre"),
    CONFIRM_ADDRESS("Is this the correct address?"),
    ERROR_PAGE("Error - ONS Surveys");

    private String id;

    private PageId(String id) {
      this.id = id;
    }

    String getId() {
      return id;
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
      if (pageContent.contains(currPageId.getId())) {
        actualPage = currPageId;
        break;
      }
    }

    // Check if we are on the expected page
    String exceptionText = null;
    if (actualPage == null) {
      String pageTitle = StringUtils.substringsBetween(pageContent, "<title>", "</title>")[0];
      exceptionText = "Failed to identify page. Page title: '" + pageTitle + "'";
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
