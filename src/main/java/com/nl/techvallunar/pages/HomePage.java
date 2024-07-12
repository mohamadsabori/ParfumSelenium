package com.nl.techvallunar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By cookiesAcceptanceButton = By.cssSelector("");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public void navigateToHomePage(String url){
        driver.get(url);
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState")).equals("complete");
    }

    public void acceptCookies() {
        By rootLocator = By.id("usercentrics-root");
        var shadowRoot = wait.until(ExpectedConditions.visibilityOfElementLocated(rootLocator)).getShadowRoot();
        wait.until(ExpectedConditions.elementToBeClickable(shadowRoot.findElement(cookiesAcceptanceButton))).click();
    }
}
