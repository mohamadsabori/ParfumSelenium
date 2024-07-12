package com.nl.techvallunar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private By cookiesAcceptanceButton = By.cssSelector("");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public void navigateToHomePage(String url){
        driver.get(url);
        driver.manage().window().maximize();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)).executeScript("return document.readyState").equals("complete");
    }

    public void acceptCookies() {
        By rootLocator = By.id("usercentrics-root");
        wait.until(ExpectedConditions.elementToBeClickable(rootLocator));
        SearchContext shadowRoot = driver.findElement(rootLocator).getShadowRoot();
        wait.until(ExpectedConditions.elementToBeClickable(shadowRoot.findElement(cookiesAcceptanceButton))).click();
    }
}
