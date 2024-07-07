package com.Orange.PageObjects;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(name = "username")
    WebElementFacade username;

    @FindBy(name = "password")
    WebElementFacade password;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    WebElementFacade submitButton;

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p")
    WebElementFacade errorMessage;

    @Step("launch the browser")
    public void launchBrowser(String url) {
        openUrl(url);
    }
    @Step("Enter Username")
    public void inputUserName(String userName) {
        username.sendKeys((userName));
    }

    @Step("Enter Password")
    public void inputPassword(String passWord) {
        password.sendKeys((passWord));
    }

    @Step("Click Submit Button")
    public void clickLogin() {
        submitButton.click();
    }

    @Step("Error Message on unsuccessful login")
    public String errorMessage() {
        String actualErrorMessage = errorMessage.getText();
        return actualErrorMessage;
    }
}
