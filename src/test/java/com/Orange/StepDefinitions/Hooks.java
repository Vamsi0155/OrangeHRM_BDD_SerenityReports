package com.Orange.StepDefinitions;

import com.Orange.Utilities.CustomListener;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Hooks {

    /*
     * We can create many Before & After's with help order. 0 is the default value.
     * Add conditions like "@After("@browser and not @headless")".
     * Pass "Scenario" as parameter to any tag.
     *
     * BeforeStep hook is executed the AfterStep hooks will also be executed regardless of the result of the step.
     * If a step did not pass, the following step and its hooks will be skipped.
     */


    private static final Logger logger = LogManager.getLogger(Hooks.class);

    public static final ThreadLocal<String> featureName = new ThreadLocal<>();

    @BeforeStep
    public void doSomethingBeforeStep(Scenario scenario){

    }

    @AfterStep
    public void doSomethingAfterStep(Scenario scenario){

    }

    @Before
    public void beforeScenario(Scenario scenario) {

        logger.info("Entered before scenario");
        featureName.set(new File(scenario.getUri()).getName());

        String featurePath = scenario.getUri().getPath();
        CustomListener.getTestStartedLog(scenario.getName(), featurePath.substring(featurePath.indexOf("src")));

        System.out.println("********** " + scenario.getName() + " is started *********");
    }


    @After
    public void afterScenario(Scenario scenario) {

        logger.info("Browser is closed");
        System.out.println("********** " + scenario.getName() + " is completed *********");

        CustomListener.getTestStatusLog(scenario.getName(), scenario.getStatus().toString());
        logger.info("Entered after scenario");
    }

    @BeforeAll
    public static void beforeAll() {
        // Runs before all scenarios

        logger.info("******************* Test Suit Started **********************");
        System.out.println("******************* Run Started **********************");
    }

    @AfterAll
    public static void afterAll() {
        // Runs after all scenarios

        System.out.println("******************* Run Completed **********************");
        logger.info("******************* Test Suit Completed **********************");
    }

}