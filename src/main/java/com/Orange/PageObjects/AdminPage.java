package com.Orange.PageObjects;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends PageObject {

    @FindBy(xpath="//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']")
    WebElementFacade search_UserName;

    @FindBy(xpath="//button[normalize-space()='Search']")
    WebElementFacade searchBtn;

    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']")
    WebElementFacade resultMsg;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    WebElementFacade addUserBtn;

    @FindBy(xpath = "//div[@class='oxd-table-card']/div/div[6]/div/button[2]")
    WebElementFacade editBtn;

    @FindBy(xpath = "//div[@class='oxd-table-card']/div/div[6]/div/button[1]")
    WebElementFacade deleteBtn;

    @FindBy(xpath = "//button[normalize-space()='Yes, Delete']")
    WebElementFacade yesDeleteBtn;

    @FindBy(xpath = "//button[normalize-space()='No, Cancel']")
    WebElementFacade noCancelBtn;


    @Step("enter user name for search")
    public void enterUserName(String userName){
        search_UserName.sendKeys(userName);
    }
    @Step("click on search button")
    public void clkSearchBtn() {
        searchBtn.click();
    }

    @Step("fetch result message")
    public String fetchResultsMsg() {
        waitFor(resultMsg);
        return resultMsg.getText();
    }

    @Step("click on add user")
    public void addUserBtn() {
        addUserBtn.click();
    }

    @Step("select Admin Role")
    public void selectAdminRole() {
        find(By.xpath("//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]")).click();
        find(By.xpath("//div[@class='oxd-select-wrapper']/div[2]/div[2]")).click();
    }
    @Step("select ESS Role")
    public void selectESSRole() {
        find(By.xpath("//div[@class='oxd-grid-2 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//div[2]//i[1]")).click();
        find(By.xpath("//div[@class='oxd-select-wrapper']/div[2]/div[3]")).click();
    }
    @Step("enter employee name")
    public void enterEmployeName(String name) {
        try {
            find(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys(name);
            Thread.sleep(2000);
            find(By.xpath("//div[@class='oxd-autocomplete-wrapper']/div[2]/div[1]")).click();
        }
        catch (Exception e){
        }
    }

    @Step("select enabled status")
    public void selectEnabledOption() {
        find(By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]")).click();
        find(By.xpath("//div[@class='oxd-select-wrapper']/div[2]/div[2]")).click();
    }

    @Step("select disabled status")
    public void selectDisabledOption() {
        find(By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]")).click();
        find(By.xpath("//div[@class='oxd-select-wrapper']/div[2]/div[3]")).click();
    }

    @Step("enter new user name")
    public void enterNewUserName(String name) {
        find(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys(name);
    }

    @Step("enter password")
    public void enterNewPassword(String password) {
        find(By.xpath("(//input[@type='password'])[1]")).sendKeys(password);
    }

    @Step("enter confirm password")
    public void enterNewConfirmPassword(String password) {
        find(By.xpath("(//input[@type='password'])[2]")).sendKeys(password);
    }

    @Step("click on save button")
    public void clkOnSaveBtn() {
        find(By.xpath("//button[normalize-space()='Save']")).click();
    }

    @Step("click on Edit option")
    public void clkOnEditOption() {
        editBtn.click();
    }

    @Step("click on Delete option")
    public void clkOnDeleteOption() {
        deleteBtn.click();
    }

    @Step("fetch the user name")
    public String fetchUserName() {
        return find(By.xpath("//div[@class='oxd-table-card']/div/div[2]/div")).getText();
    }

    @Step("fetch the user role")
    public String fetchUserRole() {
        return find(By.xpath("//div[@class='oxd-table-card']/div/div[3]/div")).getText();
    }

    @Step("fetch the Employee name")
    public String fetchEmployeeName() {
        return find(By.xpath("//div[@class='oxd-table-card']/div/div[4]/div")).getText();
    }

    @Step("fetch the status")
    public String fetchStatus() {
        return find(By.xpath("//div[@class='oxd-table-card']/div/div[5]/div")).getText();
    }

    @Step("click on Yes,Delete option")
    public void clkOnYesDeleteOption() {
        yesDeleteBtn.click();
    }

    @Step("click on No,Cancel option")
    public void clkOnNoCancelOption() {
        noCancelBtn.click();
    }

}
