package com.Orange.StepDefinitions;

import com.Orange.Factory.Loader;
import com.Orange.Factory.ReadConfigFiles;
import com.Orange.PageObjects.AdminPage;
import com.Orange.PageObjects.HomePage;
import com.Orange.PageObjects.LoginPage;
import com.Orange.Utilities.CommonMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.Map;

public class CommonSteps {


    @Steps
    LoginPage loginPage;

    @Steps
    HomePage homePage;

    @Steps
    AdminPage adminPage;

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);

    private static String user = "";

    public void waitFewSeconds(long sec) {
        try {
            Thread.sleep(sec);
        } catch (Exception e) {
        }
    }
    public void loginApplication(String userName, String password) {

        if(loginPage.isElementVisible(By.xpath("//h5[normalize-space()='Login']"))) {
            loginPage.inputUserName(userName);
            loginPage.inputPassword(password);
            loginPage.clickLogin();
        }
        else {
            logger.info("Login is not displayed, doing refresh the page");
            loginPage.getDriver().navigate().refresh();
            loginPage.inputUserName(userName);
            loginPage.inputPassword(password);
            loginPage.clickLogin();
        }
    }

    @Given("launch the browser")
    public void launch_the_browser() {

        loginPage.openUrl(ReadConfigFiles.config.getProperty("baseUrl"));
        logger.info("Browser launched successfully");
    }

    @Given("{string} login into the Application")
    public void login_into_the_application(String user) {

        String userName;
        String password;

        if(user.equalsIgnoreCase("Admin")) {

            userName = ReadConfigFiles.config.getProperty("Admin");
            password = ReadConfigFiles.config.getProperty("password");

            loginApplication(userName, password);
        }
        else {

            password = Loader.getGlobalValues().get(user);
            loginApplication(user, password);
        }

        try {
            loginPage.shouldBeVisible(By.xpath("//h6[normalize-space()='Dashboard']"));
            logger.info("logged into the application successfully");
        } catch (NoSuchElementException e) {
            logger.error("Dashboard is not displayed, ", e);
            Assertions.fail("Dashboard is not displayed");
        }
    }

    @Given("click on the {string} of Menu Bar")
    public void click_on_the_of_menu_bar(String item) {

        switch (item) {
            case "Admin" -> {
                homePage.AdminItem();
            }
            case "PIM" -> {
                homePage.PIMItem();
            }
            case "Leave" -> {
                homePage.LeaveItem();
            }
            case "Time" -> {
                homePage.TimeItem();
            }
            case "Recruitment" -> {
                homePage.RecruitmentItem();
            }
            case "My Info" -> {
                homePage.myInfoItem();
            }
        }
        logger.info("clicked on the "+item+" of Menu Bar");
    }

    @Given("let's search for user if already existing?")
    public void let_s_search_for_user_if_already_existing(DataTable table) throws InterruptedException {

        String userName = table.asMaps().getFirst().get("User Name");
        adminPage.enterUserName(userName);
        adminPage.clkSearchBtn();

        String text = adminPage.fetchResultsMsg();
        Assertions.assertEquals("No Records Found", text);
        logger.info("No user was existing with {}", userName);
        Loader.setGlobalValue("User Name", userName);
    }

    @When("created a new user with below details:")
    public void created_a_new_user_with_below_details(DataTable dataTable) {

        Map<String, String> newTable = CommonMethods.parseGivenInputTable(dataTable);

        waitFewSeconds(2000);
        adminPage.addUserBtn();

        waitFewSeconds(2000);
        if(newTable.get("User Role").equals("Admin")) {
            adminPage.selectAdminRole();
        }
        else {
           adminPage.selectESSRole();
        }

        adminPage.enterEmployeName(newTable.get("Employee Name"));
        if(newTable.get("Status").equals("Enabled")) {
            adminPage.selectEnabledOption();
        }
        else {
            adminPage.selectDisabledOption();
        }

        adminPage.enterNewUserName(newTable.get("User Name"));
        adminPage.enterNewPassword(newTable.get("Password"));
        adminPage.enterNewConfirmPassword(newTable.get("Password"));

        Loader.setGlobalValue(newTable.get("User Name"), newTable.get("Password"));
        System.out.println("Pass: " + newTable.get("Password"));

        waitFewSeconds(2000);
        adminPage.clkOnSaveBtn();
        logger.info("User created with below details");
        Serenity.recordReportData().withTitle("User Name").andContents(newTable.get("User Name"));
        Serenity.recordReportData().withTitle("Password").andContents(newTable.get("Password"));
        user = newTable.get("User Name");
    }

    @Then("verify below message")
    public void verify_below_message(DataTable dataTable) {

       String text = adminPage.fetchResultsMsg();
       Assertions.assertEquals(dataTable.asMaps().getFirst().get("Message"), text);
       logger.info("Below message is verified");
    }

    @Then("logout from the Application")
    public void logout_from_the_application() {

        waitFewSeconds(2000);
        homePage.logOutDropDown().click();
        waitFewSeconds(2000);
        homePage.clkOnLogOut();
        logger.info("Logged out from application");
        logger.info("Browser is closed");
    }

    @When("click on the {string} option")
    public void click_on_the_option(String option) {

        waitFewSeconds(2000);
        int rowCount = adminPage.findAll(By.xpath("//div[@class='oxd-table-body']/div")).size();
        if(rowCount == 1) {
            if(option.equals("Edit")) {
                adminPage.clkOnEditOption();
                Serenity.recordReportData().withTitle("Modified User").andContents(user);
            }
            else if(option.equals("Delete")) {
                adminPage.clkOnDeleteOption();
                Serenity.recordReportData().withTitle("Deleted User").andContents(user);
            }
        }
        logger.info("clicked on the " + option + " option");
    }

    @Given("search for the user")
    @When("fetch the user details from the list")
    public void fetch_the_user_details() {

        String userName = Loader.getGlobalValues().get("User Name");
        adminPage.enterUserName(userName);
        adminPage.clkSearchBtn();

        Assertions.assertTrue(adminPage.isElementVisible(By.xpath("//span[normalize-space()='(1) Record Found']")), "Either no user (or) more than 1 user found..");
        logger.info("User is found and fetching details...");
        adminPage.evaluateJavascript("window.scrollTo(0, document.body.scrollHeight)");
        logger.info("searched/fetched user details");
    }

    @Then("verify the new user below details:")
    public void verify_user_details(DataTable table) {

        waitFewSeconds(2000);
        int rowCount = adminPage.findAll(By.xpath("//div[@class='oxd-table-body']/div")).size();
        if(rowCount > 1) {
            System.out.println("Rows: " + rowCount);
        }
        else {
            String user = adminPage.fetchUserName();
            Assertions.assertEquals(Loader.getGlobalValues().get("User Name"), user, "Invalid user found..");

            String role = adminPage.fetchUserRole();
            Assertions.assertEquals(table.asMaps().getFirst().get("User Role"), role, "Invalid role found..");

            String empName = adminPage.fetchEmployeeName();
            Assertions.assertEquals(table.asMaps().getFirst().get("Employee Name"), empName, "Invalid Employee name found..");

            String status = adminPage.fetchStatus();
            Assertions.assertEquals(table.asMaps().getFirst().get("Status"), status, "Invalid status found..");
        }
        logger.info("new user is verified with below details");
    }

    @When("Modify the user with below details:")
    public void modify_user_details(DataTable table) {

        adminPage.shouldBeVisible(By.xpath("//button[normalize-space()='Save']"));
        adminPage.selectDisabledOption();
        waitFewSeconds(2000);
        adminPage.clkOnSaveBtn();
        logger.info("user modified with below details");
    }

    @When("Confirm with {string} option")
    public void confirm_with_option(String option) {

        waitFewSeconds(2000);
        if(option.equals("Yes, Delete")) {
            adminPage.clkOnYesDeleteOption();
        } else if (option.equals("No, Cancel")) {
            adminPage.clkOnNoCancelOption();
        }
        logger.info("Confirmed with " + option);
    }


    @Then("verify the new user")
    public void verifyTheNewUser() {

        homePage.waitFor(homePage.logOutDropDown());
        String userName = homePage.logOutDropDown().getText();
        System.out.println("User: " + userName);
        logger.info("new user is verified");
    }
}
