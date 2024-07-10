package com.nl.techvallunar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GoogleSearchTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testDouglas() {
        driver.get("https://www.douglas.de/de");
        printAllElements(driver);
    }

    private static void printAllElements(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000L)); // 10 seconds timeout

        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        // Iterate through each element and check if it's visible
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                System.out.println("Tag Name: " + element.getTagName());
            }
        }
    }

    @AfterAll
    public static void TearDown() {
        if (driver != null)
            driver.quit();
    }
    
}
