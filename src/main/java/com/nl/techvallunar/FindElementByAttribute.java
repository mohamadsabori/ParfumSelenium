package com.nl.techvallunar;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindElementByAttribute {

    public static void main(String[] args) {
        // Specify the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "./chromedriver-linux64/chromedriver");

        // Optional: Set Chrome options (e.g., headless mode)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment to run in headless mode

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the webpage
        driver.get("https://www.douglas.de/de");

        // Introduce a delay (if needed)
        try {
            Thread.sleep(2000); // 2 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printAllElements(driver);
        // Find the div element with data-testid="uc-heading-title"
        // Close the browser
        driver.quit();
    }

    private static void printAllElements(WebDriver driver){
        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        // Iterate through each element and check if it's visible
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                // Print the tag name and text of visible elements
                System.out.println("Tag Name: " + element.getTagName() + ", Text: " + element.getText().substring(0,2));
            }
        }
    }

    private static void handleCookies(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000L)); // 10 seconds timeout
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='uc-heading-title' and @id='uc-heading-title']")));
        if (element.isDisplayed()) {
            System.out.println("Found element with data-testid='uc-heading-title'");
            // Perform further actions (e.g., click, get text, etc.)
        } else {
            System.out.println("Element with data-testid='uc-heading-title' not found or not visible");
        }
    }
}
