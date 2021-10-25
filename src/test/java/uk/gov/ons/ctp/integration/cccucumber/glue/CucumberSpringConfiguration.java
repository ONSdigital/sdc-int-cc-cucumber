package uk.gov.ons.ctp.integration.cccucumber.glue;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.ons.ctp.common.cloud.FirestoreDataStore;
import uk.gov.ons.ctp.common.cloud.TestCloudDataStore;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.Pages;

@CucumberContextConfiguration
@EnableConfigurationProperties
@SpringBootTest(
    classes = {
      GlueContext.class,
      WebDriverFactory.class,
      TestCloudDataStore.class,
      FirestoreDataStore.class,
      Pages.class,
      FirestoreDataStore.class
    })
public class CucumberSpringConfiguration {}
