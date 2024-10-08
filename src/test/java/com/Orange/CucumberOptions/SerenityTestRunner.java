package com.Orange.CucumberOptions;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class SerenityTestRunner {

        /*
        @RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/Admin_Module/Multi_User_Creation.feature"},
        glue = {"com.Orange.StepDefinitions"},
        monochrome = true,
        plugin = {
                "pretty",
                "com.Orange.Utilities.CustomListener",
                "rerun:target/failedScenariosPath.txt"
        }
)
         */
}
