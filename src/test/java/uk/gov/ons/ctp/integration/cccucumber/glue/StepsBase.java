package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.ons.ctp.common.event.TopicType;
import uk.gov.ons.ctp.common.event.model.FulfilmentEvent;
import uk.gov.ons.ctp.common.event.model.GenericEvent;
import uk.gov.ons.ctp.common.event.model.SurveyLaunchEvent;
import uk.gov.ons.ctp.common.event.model.UacAuthenticateEvent;
import uk.gov.ons.ctp.common.pubsub.PubSubHelper;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.data.ExampleData;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.Pages;

public abstract class StepsBase {
  static final long PUBSUB_TIMEOUT_MS = 20000;

  @Autowired CcSvcDatabase db;
  @Autowired GlueContext context;
  @Autowired WebDriverFactory webDriverFactory;
  @Autowired Pages pages;

  @Value("${pubsub.projectid}")
  private String pubsubProjectId;

  @Value("${pubsub.emulator.host}")
  private String emulatorPubSubHost;

  @Value("${pubsub.emulator.use}")
  private boolean useEmulatorPubSub;

  WebDriver driver;
  PubSubHelper pubSub;

  public void setupForAll() throws Exception {
    db.deleteSurveyCascade(ExampleData.DEFAULT_SURVEY_ID);
    assertFalse(db.caseExists(context.caseKey));
    pubSub = PubSubHelper.instance(pubsubProjectId, false, useEmulatorPubSub, emulatorPubSubHost);
    driver = pages.getWebDriver();
  }

  void closeDriver() {
    webDriverFactory.closeWebDriver(driver);
  }

  void destroyPubSub() {
    PubSubHelper.destroy();
  }

  void verifyCorrectOnsLogoUsed(WebElement logo) {
    String expectedLogoTextId = "ons-logo-en-alt";
    assertNotNull(logo);
    String actualLogoTextId = extractLogoName(logo.getAttribute("id"));
    assertEquals("name found for logo is incorrect", expectedLogoTextId, actualLogoTextId);
  }

  String extractLogoName(String sourceFound) {
    int fileNameStart = sourceFound.lastIndexOf("/");
    String logoName = sourceFound.substring(fileNameStart + 1);
    return logoName;
  }

  // - event validation helpers ...

  void emptyEventQueue(TopicType eventType) throws Exception {
    pubSub.flushSubscription(eventType);
  }

  void assertNewEventHasFired(TopicType topicType) throws Exception {
    final GenericEvent event =
        (GenericEvent) pubSub.getMessage(topicType, eventClass(topicType), PUBSUB_TIMEOUT_MS);
    assertNotNull(event);
    assertNotNull(event.getHeader());
  }

  Class<?> eventClass(TopicType topicType) {
    switch (topicType) {
      case FULFILMENT:
        return FulfilmentEvent.class;
      case UAC_AUTHENTICATE:
        return UacAuthenticateEvent.class;
      case SURVEY_LAUNCH:
        return SurveyLaunchEvent.class;
      default:
        throw new IllegalArgumentException("Cannot create event for topic type: " + topicType);
    }
  }
}
