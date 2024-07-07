package com.Orange.CucumberOptions;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.Orange.StepDefinitions"},
        monochrome = true,
        plugin = {
                "pretty",
                "com.Orange.Utilities.CustomListener",
                "rerun:target/failedScenariosPath.txt"
        }
)
public class SerenityTestRunner {

}
