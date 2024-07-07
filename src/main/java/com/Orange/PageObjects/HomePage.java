package com.Orange.PageObjects;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    WebElementFacade dashBoardEle;

    /* Menu Bar Elements */
    @FindBy(xpath="//li[1]//a[1]//span[1]")
    WebElementFacade Admin;

    @FindBy(xpath="//li[2]//a[1]//span[1]")
    WebElementFacade PIM;

    @FindBy(xpath="//li[3]//a[1]//span[1]")
    WebElementFacade Leave;

    @FindBy(xpath="//li[4]//a[1]//span[1]")
    WebElementFacade Time;

    @FindBy(xpath="//li[5]//a[1]//span[1]")
    WebElementFacade Recruitment;

    @FindBy(xpath="//li[6]//a[1]//span[1]")
    WebElementFacade MyInfo;

    @FindBy(xpath = "//p[@class='oxd-userdropdown-name']")
    WebElementFacade logOutDropDown;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    WebElementFacade logOutBtn;

    @Step("click on Admin")
    public void AdminItem() {
        Admin.click();
    }
    @Step("click on PIM")
    public void PIMItem() {
        PIM.click();
    }
    @Step("click on Leave")
    public void LeaveItem() {
        Leave.click();
    }
    @Step("click on Time")
    public void TimeItem() {
        Time.click();
    }
    @Step("click on Recruitment")
    public void RecruitmentItem() {
        Recruitment.click();
    }
    @Step("click on My info")
    public void myInfoItem() {
        MyInfo.click();
    }

    @Step("log-out drop down")
    public WebElementFacade logOutDropDown() {
        return find(By.xpath("//p[@class='oxd-userdropdown-name']"));
    }

    @Step("click on log-out")
    public void clkOnLogOut() {
        find(By.xpath("//a[normalize-space()='Logout']")).click();
    }


}
