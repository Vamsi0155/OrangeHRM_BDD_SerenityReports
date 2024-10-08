package com.Orange.StepDefinitions;

import com.Orange.Factory.Loader;
import com.Orange.Factory.ReadConfigFiles;
import com.Orange.Utilities.CommonMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import net.serenitybdd.core.Serenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;

import java.util.Map;

import static com.Orange.StepDefinitions.Hooks.featureName;


public class CommonSteps extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);

    public void loginApplication(String userName, String password) {

        if(element("xpath", "//h5[normalize-space()='Login']").isDisplayed()) {

            element("xpath", "//input[@placeholder='Username']").sendKeys(userName);
            enterText("xpath", "//input[@placeholder='Password']", password);
            clickButton("xpath", "//button[normalize-space()='Login']");
        }
        else {
            logger.info("Login is not displayed, doing refresh the page");
            refreshPage();

            element("xpath", "//input[@placeholder='Username']").sendKeys(userName);
            enterText("xpath", "//input[@placeholder='Password']", password);
            clickButton("xpath", "//button[normalize-space()='Login']");
        }
    }

    @Given("launch the browser")
    public void launch_the_browser() {

        loadBrowser();
        logger.info("Browser launched successfully");
    }

    @Given("{string} login into the Application")
    public void login_into_the_application(String user) {

        //System.out.println("Feature name is: " + getFeatureName(featureName.get()));
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
            wait_for_Element_until_display(10, "//h6[normalize-space()='Dashboard']");
            element("xpath", "//h6[normalize-space()='Dashboard']").isDisplayed();
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
                clickButton("xpath", "//li[1]//a[1]//span[1]");
            }
            case "PIM" -> {
                clickButton("xpath", "//li[2]//a[1]//span[1]");
            }
            case "Leave" -> {
                clickButton("xpath", "//li[3]//a[1]//span[1]");
            }
            case "Time" -> {
                clickButton("xpath", "//li[4]//a[1]//span[1]");
            }
            case "Recruitment" -> {
                clickButton("xpath", "//li[5]//a[1]//span[1]");
            }
            case "My Info" -> {
                clickButton("xpath", "//li[6]//a[1]//span[1]");
            }default -> {
                // Dashboard
                clickButton("xpath", "//li[8]//a[1]//span[1]");
            }
        }
        logger.info("clicked on the "+item+" of Menu Bar");
    }

    @Given("let's search for user if already existing?")
    public void let_s_search_for_user_if_already_existing(DataTable table) throws InterruptedException {

        String userName = table.asMaps().getFirst().get("User Name");
        enterText("xpath", "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']", userName);
        clickButton("xpath", "//button[normalize-space()='Search']");

        String text = element("xpath", "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']").getText();
        Serenity.reportThat("Already user existing!!",
                () -> Assertions.assertEquals("No Records Found", text));
        logger.info("No user was existing with {}", userName);
        Loader.setGlobalValue("User Name", userName);
    }

    @When("created a new user with below details:")
    public void created_a_new_user_with_below_details(DataTable dataTable) {

        Map<String, String> newTable = CommonMethods.parseGivenInputTable(dataTable);

        waitFewSeconds(2000);
        clickButton("xpath", "//button[normalize-space()='Add']");

        waitFewSeconds(2000);
        if(newTable.get("User Role").equals("Admin")) {
            clickButton("xpath", "//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]");
            clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[2]");
        }
        else {
            clickButton("xpath", "//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]");
            clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        }

        enterText("xpath", "//input[@placeholder='Type for hints...']", newTable.get("Employee Name"));
        waitFewSeconds(2000);
        clickButton("xpath", "//div[@class='oxd-autocomplete-wrapper']/div[2]/div[1]");

        if(newTable.get("Status").equals("Enabled")) {
            clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
            clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[2]");
        }
        else {
            clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
            clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        }

        enterText("xpath", "(//input[@class='oxd-input oxd-input--active'])[2]", newTable.get("User Name"));
        enterText("xpath", "(//input[@type='password'])[1]", newTable.get("Password"));
        enterText("xpath", "(//input[@type='password'])[2]", newTable.get("Password"));
        Loader.setGlobalValue(newTable.get("User Name"), newTable.get("Password"));
        System.out.println("Pass: " + newTable.get("Password"));

        waitFewSeconds(2000);
        clickButton("xpath", "//button[normalize-space()='Save']");
        logger.info("User created with below details");
        Serenity.recordReportData().withTitle("Password").andContents(newTable.get("Password"));
    }

    @Then("verify below message")
    public void verify_below_message(DataTable dataTable) {

        wait_for_Element_until_display(10, "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']");
        String text = element("xpath", "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']").getText();
        //Assertions.assertEquals(dataTable.asMaps().getFirst().get("Message"), text);
        Serenity.reportThat("Below error message generated:",
                () -> Assertions.assertEquals(dataTable.asMaps().getFirst().get("Message"), text));
        logger.info("Below message is verified");
    }

    @Then("logout from the Application")
    public void logout_from_the_application() {

        waitFewSeconds(4000);
        clickButton("xpath", "//p[@class='oxd-userdropdown-name']");
        clickButton("xpath", "//a[normalize-space()='Logout']");
        logger.info("Logged out from application");
    }

    //@Given("search for the user")
    public void search_for_the_user() {


    }

    @When("click on the {string} option")
    public void click_on_the_option(String option) {

        waitFewSeconds(2000);
        int rowCount = elementList("xpath", "//div[@class='oxd-table-body']/div").size();

        if(rowCount == 1) {
            if(option.equals("Edit")) {
                clickButton("xpath", "//div[@class='oxd-table-card']/div/div[6]/div/button[2]");
            }
            else if(option.equals("Delete")) {
                clickButton("xpath", "//div[@class='oxd-table-card']/div/div[6]/div/button[1]");
            }
        }
        logger.info("clicked on the " + option + " option");
    }

    @Given("search for the user")
    @When("fetch the user details from the list")
    public void fetch_the_user_details() {

        String userName = Loader.getGlobalValues().get("User Name");
        enterText("xpath", "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']", userName);
        clickButton("xpath", "//button[normalize-space()='Search']");

        Assertions.assertTrue(element("xpath", "//span[normalize-space()='(1) Record Found']").isDisplayed(), "Either no user (or) more than 1 user found..");
        logger.info("User is found and fetching details...");
        pageScrollBottom();
        logger.info("searched/fetched user details");
    }

    @Then("verify the new user below details:")
    public void verify_user_details(DataTable table) {

        waitFewSeconds(2000);
        int rowCount = elementList("xpath", "//div[@class='oxd-table-body']/div").size();
        if(rowCount > 1) {
            System.out.println("Rows: " + rowCount);
        }
        else {
            String user = element("xpath", "//div[@class='oxd-table-card']/div/div[2]/div").getText();
            Assertions.assertEquals(Loader.getGlobalValues().get("User Name"), user, "Invalid user found..");

            String role = element("xpath", "//div[@class='oxd-table-card']/div/div[3]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("User Role"), role, "Invalid role found..");

            String empName = element("xpath", "//div[@class='oxd-table-card']/div/div[4]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("Employee Name"), empName, "Invalid Employee name found..");

            String status = element("xpath", "//div[@class='oxd-table-card']/div/div[5]/div").getText();
            Assertions.assertEquals(table.asMaps().getFirst().get("Status"), status, "Invalid status found..");
        }
        logger.info("new user is verified with below details");
    }

    @When("Modify the user with below details:")
    public void modify_user_details(DataTable table) {

        wait_for_Element_until_display(10, "//button[normalize-space()='Save']");
        clickButton("xpath", "//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]");
        clickButton("xpath", "//div[@class='oxd-select-wrapper']/div[2]/div[3]");
        waitFewSeconds(2000);
        clickButton("xpath", "//button[normalize-space()='Save']");
        logger.info("user modified with below details");
    }

    @When("Confirm with {string} option")
    public void confirm_with_option(String option) {

        waitFewSeconds(2000);
        if(option.equals("Yes, Delete")) {

            clickButton("xpath", "//button[normalize-space()='Yes, Delete']");
        } else if (option.equals("No, Cancel")) {

            clickButton("xpath", "//button[normalize-space()='No, Cancel']");
        }
        logger.info("Confirmed with " + option);
    }


    @Then("verify the new user")
    public void verifyTheNewUser() {

        wait_for_Element_until_display(15, "//p[@class='oxd-userdropdown-name']");
        String userName = element("xpath", "//p[@class='oxd-userdropdown-name']").getText();
        System.out.println("User: " + userName);
        logger.info("new user is verified");
    }
}