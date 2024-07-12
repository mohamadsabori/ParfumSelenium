package com.nl.techvallunar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PerfumePage extends BasePage{
    public PerfumePage(WebDriver driver){
        super(driver);
    }

    public void clickPerfumeSection(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='PARFUM']"))).click();
    }
}
