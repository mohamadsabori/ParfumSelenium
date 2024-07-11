package com.nl.techvallunar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.SearchContext;
import java.time.Duration;

public class TestDouglas {
    
    static WebDriver driver = new ChromeDriver();
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "./chromedriver-linux64/chromedriver");
        try {
            driver.get("https://www.douglas.de/");
            driver.manage().window().maximize();
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            String expectedTitle = "Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS";
            wait.until(ExpectedConditions.titleIs(expectedTitle));
            if (driver.getTitle().equals(expectedTitle)) {
                handleCookiesSection();
                
                handlePerfumeSection();
                
                firstSearchScenario();

                secoundSeachScenario();

                try {
                    Thread.sleep(5000);    
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Page title is incorrect. Actual title: " + driver.getTitle());
            }
        } finally {
            driver.quit();
        }
    }

    private static void searchByMarke() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Marke')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Marke')]")).click();
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Marke suchen']")));
        driver.findElement(By.xpath("//input[@placeholder='Marke suchen']")).click();
        new Actions(driver)
            .sendKeys("4711")
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Marke']")).click();
    }

    private static void handlePerfumeSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='PARFUM']")));
        driver.findElement(By.xpath("//a[normalize-space()='PARFUM']")).click();
        wait.until(ExpectedConditions.titleIs("Parfüm & Düfte ✔️ online kaufen » für Sie & Ihn | DOUGLAS"));
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleCookiesSection() {
        try {
            Thread.sleep(1000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        SearchContext shadow = driver.findElement(By.id("usercentrics-root")).getShadowRoot();
        try {
            Thread.sleep(1000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        shadow.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz")).click();
        try {
            Thread.sleep(1000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearSearchBar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")).click();   
    }

    private static void searchByProduktart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Produktart')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Produktart')]")).click();
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Produktart suchen']")));
        driver.findElement(By.xpath("//input[@placeholder='Produktart suchen']")).click();
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Produktart']")).click();
    }

    private static void firstSearchScenario() {
        searchByMarke();

        searchByProduktart();
        // Spent some times to see the result
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearSearchBar();
    }

    private static void secoundSeachScenario() {
        searchByHighlights();

        // Spent some times to see the result
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearSearchBar();
    }

    private static void searchByHighlights() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Highlights')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Highlights')]")).click();
        try {
            Thread.sleep(5000);    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Highlights']")).click();
    }
}

