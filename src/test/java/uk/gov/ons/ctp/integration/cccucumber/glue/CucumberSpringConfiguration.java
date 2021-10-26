package uk.gov.ons.ctp.integration.cccucumber.glue;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uk.gov.ons.ctp.common.cloud.FirestoreDataStore;
import uk.gov.ons.ctp.common.cloud.TestCloudDataStore;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.Pages;

@CucumberContextConfiguration
@EnableConfigurationProperties
@EnableJpaRepositories
@AutoConfigureDataJpa
@EntityScan("uk.gov.ons.ctp.integration.cccucumber.data.db")
@SpringBootTest(
    classes = {
      GlueContext.class,
      WebDriverFactory.class,
      TestCloudDataStore.class,
      FirestoreDataStore.class,
      Pages.class,
      FirestoreDataStore.class,
      CaseRepository.class
    })
public class CucumberSpringConfiguration {}
