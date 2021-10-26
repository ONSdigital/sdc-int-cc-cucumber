package uk.gov.ons.ctp.integration.cccucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import uk.gov.ons.ctp.common.event.TopicType;
import uk.gov.ons.ctp.common.event.model.FulfilmentEvent;
import uk.gov.ons.ctp.common.event.model.GenericEvent;
import uk.gov.ons.ctp.common.event.model.SurveyLaunchEvent;
import uk.gov.ons.ctp.common.event.model.UacAuthenticateEvent;
import uk.gov.ons.ctp.common.pubsub.PubSubHelper;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.Pages;

public abstract class StepsBase {
  static final long PUBSUB_TIMEOUT_MS = 20000;
  static final long WAIT_TIMEOUT = 20_000L;

  @Autowired CaseRepository caseRepo;
  @Autowired GlueContext context;
  @Autowired WebDriverFactory webDriverFactory;
  @Autowired Pages pages;

  @Value("${keystore}")
  String keystore;

  @Value("${pubsub.projectid}")
  private String pubsubProjectId;

  @Value("${pubsub.emulator.host}")
  private String emulatorPubSubHost;

  @Value("${pubsub.emulator.use}")
  private boolean useEmulatorPubSub;

  WebDriver driver;
  PubSubHelper pubSub;

  public void setupForAll() throws Exception {
    // dataRepo.deleteCollections();
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

  void assertNewEventHasFired(TopicType eventType) throws Exception {

    final GenericEvent event =
        (GenericEvent) pubSub.getMessage(eventType, eventClass(eventType), PUBSUB_TIMEOUT_MS);

    assertNotNull(event);
    assertNotNull(event.getHeader());
  }

  Class<?> eventClass(TopicType eventType) {
    switch (eventType) {
      case FULFILMENT:
        return FulfilmentEvent.class;
      case UAC_AUTHENTICATE:
        return UacAuthenticateEvent.class;
      case SURVEY_LAUNCH:
        return SurveyLaunchEvent.class;
      default:
        throw new IllegalArgumentException("Cannot create event for event type: " + eventType);
    }
  }

  void verifyCaseExistsInCCSvcDatabase(UUID caseId) {
    caseRepo.findById(caseId).orElseThrow();
  }

  <T> void waitForCreation(UUID id, CrudRepository<T, UUID> repo) {
    // WRITEME
  }
}
