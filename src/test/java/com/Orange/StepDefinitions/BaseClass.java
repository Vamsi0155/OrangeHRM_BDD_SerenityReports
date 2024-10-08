package com.Orange.StepDefinitions;

import com.Orange.Factory.ReadConfigFiles;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class BaseClass extends PageObject {


    public void loadBrowser() {

        String url = ReadConfigFiles.config.getProperty("baseUrl");
        openUrl(url);
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void closeBrowser() {
        getDriver().quit();
    }

    public String getFeatureName(String name) {
        return name.substring(0, name.lastIndexOf(".feature"));
    }



    /*
        All common Actions methods starts here.
     */
    public WebElement element(String type, String value) {

        return switch (type) {
            case "id" -> find(By.id(value));
            case "name" -> find(By.name(value));
            case "className" -> find(By.className(value));
            case "xpath" -> find(By.xpath(value));
            case "css" -> find(By.cssSelector(value));
            case "linkText" -> find(By.linkText(value));
            case "partLinkText" -> find(By.partialLinkText(value));
            default -> null;
        };
    }

    public List<WebElement> elementList(String type, String value) {

        if(type.equals("id")) {
            return getDriver().findElements(By.id(value));
        }
        else if(type.equals("name")) {
            return getDriver().findElements(By.name(value));
        }
        else if(type.equals("className")) {
            return getDriver().findElements(By.className(value));
        }
        else if(type.equals("xpath")) {
            return getDriver().findElements(By.xpath(value));
        }
        else if(type.equals("css")) {
            return getDriver().findElements(By.cssSelector(value));
        }
        return null;
    }

    public void clickButton(String type, String value) {

        switch (type) {
            case "id":
                find(By.id(value)).click();
            case "name":
                find(By.name(value)).click();
            case "className":
                find(By.className(value)).click();
            case "css":
                find(By.cssSelector(value)).click();
            case "linkText":
                find(By.linkText(value)).click();
            case "partLinkText":
                find(By.partialLinkText(value)).click();
            case "xpath":
                find(By.xpath(value)).click();
        }
    }

    public void enterText(String type, String value, String text) {

        switch (type) {
            case "css":
                find(By.cssSelector(value)).sendKeys(text);
            case "id":
                find(By.id(value)).sendKeys(text);
            case "name":
                find(By.name(value)).sendKeys(text);
            case "className":
                find(By.className(value)).sendKeys(text);
            case "xpath":
                find(By.xpath(value)).sendKeys(text);
            default:
        }
    }

    public Select selectDropDownBy(String type, String value) {

        return switch (type) {
            case "id" -> new Select(find(By.id(value)));
            case "name" -> new Select(find(By.name(value)));
            case "className" -> new Select(find(By.className(value)));
            case "xpath" -> new Select(find(By.xpath(value)));
            case "css" -> new Select(find(By.cssSelector(value)));
            default -> null;
        };
    }

    public void rightClick(String type, String value) {

        Actions action = new Actions(getDriver());
        switch (type) {
            case "css":
                action.contextClick(find(By.cssSelector(value))).perform();
            case "id":
                action.contextClick(find(By.id(value))).perform();
            case "name":
                action.contextClick(find(By.name(value))).perform();
            case "className":
                action.contextClick(find(By.className(value))).perform();
            case "xpath":
                action.contextClick(find(By.xpath(value))).perform();
        }
    }

    public void doubleClick(String type, String value) {

        Actions action = new Actions(getDriver());
        switch (type) {
            case "css":
                action.doubleClick(find(By.cssSelector(value))).perform();
            case "id":
                action.doubleClick(find(By.id(value))).perform();
            case "name":
                action.doubleClick(find(By.name(value))).perform();
            case "className":
                action.doubleClick(find(By.className(value))).perform();
            case "xpath":
                action.doubleClick(find(By.xpath(value))).perform();
        }
    }

    public void doDragDrop(WebElement source, WebElement target) {
        withAction().dragAndDrop(source, target).perform();
    }

    public void doDragDrop(WebElement source, int x, int y) {
        withAction().dragAndDropBy(source, x, y).perform();
    }

    public void moveCursor(WebElement target) {
        withAction().moveToElement(target).perform();
    }

    public void moveCursor(WebElement target, int x, int y) {
        withAction().moveToElement(target, x, y).perform();
    }

    public Actions doActions() {
        return new Actions(getDriver());
    }

    public void wait_for_Element_until_display(int seconds, String xpath) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void pageScrollBottom() {
        evaluateJavascript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void pageScrollTop() {
        withAction().keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).perform();
    }

    public void waitFewSeconds(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}