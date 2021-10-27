package uk.gov.ons.ctp.integration.cccucumber.glue;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;
import uk.gov.ons.ctp.common.util.WebDriverFactory;
import uk.gov.ons.ctp.integration.cccucumber.selenium.pages.Pages;

@CucumberContextConfiguration
@EnableConfigurationProperties
@AutoConfigureDataJdbc
@SpringBootTest(
    classes = {
      GlueContext.class,
      WebDriverFactory.class,
      Pages.class,
      CcSvcDatabase.class
    })
public class CucumberSpringConfiguration {}
