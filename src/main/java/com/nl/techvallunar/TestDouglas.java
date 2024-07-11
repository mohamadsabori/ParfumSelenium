package com.nl.techvallunar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class TestDouglas {

    private static final String PARFUM_XPATH = "//a[normalize-space()='PARFUM']";
    private static final String HIGHT_LIGHTS_XPATH = "//div[contains(text(),'Highlights')]";
    private static final String FILTER_BAR_XPATH = "//*[contains(text(), 'Alle Filter löschen')]";
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "./chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.douglas.de/");
            driver.manage().window().maximize();
            waitForPageLoad();

            String expectedTitle = "Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS";
            wait.until(ExpectedConditions.titleIs(expectedTitle));

            if (driver.getTitle().equals(expectedTitle)) {
                handleCookiesSection();
                handlePerfumeSection();
                firstSearchScenario();
                secondSearchScenario();
                pause(5000);  // Just to see the result before quitting, not recommended in real tests
            } else {
                System.out.println("Page title is incorrect. Actual title: " + driver.getTitle());
            }
        } finally {
            driver.quit();
        }
    }

    private static void handleCookiesSection() {
        pause(1000);
        SearchContext shadow = driver.findElement(By.id("usercentrics-root")).getShadowRoot();
        pause(1000);
        shadow.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz")).click();
        pause(1000);
    }

    private static void handlePerfumeSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PARFUM_XPATH)));
        driver.findElement(By.xpath(PARFUM_XPATH)).click();
        wait.until(ExpectedConditions.titleIs("Parfüm & Düfte ✔️ online kaufen » für Sie & Ihn | DOUGLAS"));
        pause(5000);
        moveMouse();
    }

    private static void moveMouse() {
        PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");

        Sequence actions = new Sequence(mouse, 0)
                .addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
                .addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));

        ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
    }

    private static void firstSearchScenario() {
        searchByMarke();
        searchByProduktart();
        pause(5000);  // Just to see the result
        clearSearchBar();
    }

    private static void secondSearchScenario() {
        searchByHighlights();
        pause(5000);  // Just to see the result
        clearSearchBar();
    }

    private static void searchByMarke() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Marke')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Marke')]")).click();
        pause(5000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Marke suchen']")));
        driver.findElement(By.xpath("//input[@placeholder='Marke suchen']")).click();
        new Actions(driver)
            .sendKeys("4711")
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_BAR_XPATH)));
        driver.findElement(By.xpath("//div[text()='Marke']")).click();
    }

    private static void searchByProduktart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Produktart')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Produktart')]")).click();
        pause(5000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Produktart suchen']")));
        driver.findElement(By.xpath("//input[@placeholder='Produktart suchen']")).click();
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_BAR_XPATH)));
        driver.findElement(By.xpath("//div[text()='Produktart']")).click();
    }

    private static void searchByHighlights() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HIGHT_LIGHTS_XPATH)));
        driver.findElement(By.xpath(HIGHT_LIGHTS_XPATH)).click();
        pause(5000);
        
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_BAR_XPATH)));
        driver.findElement(By.xpath("//div[text()='Highlights']")).click();
    }

    private static void clearSearchBar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_BAR_XPATH)));
        driver.findElement(By.xpath(FILTER_BAR_XPATH)).click();
    }

    private static void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
