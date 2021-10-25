package uk.gov.ons.ctp.integration.cccucumber.run;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import uk.gov.ons.ctp.integration.cccucumber.GlueConst;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber/results.html"},
    features = {
      GlueConst.FEATURES_PATH + "Feature1.feature",
      GlueConst.FEATURES_PATH + "SurveyEnquiryLine.feature"
    },
    tags = GlueConst.COMMON_TAGS,
    glue = {GlueConst.GLUE_PKG})
public class CucumberTestRunner {}
