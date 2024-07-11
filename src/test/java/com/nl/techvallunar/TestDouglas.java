package com.nl.techvallunar;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.Status;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;

public class TestDouglas {
    
    private WebDriver driver;
    private WebDriverWait wait;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Set up ExtentReports
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Selenium Test Report");
        htmlReporter.config().setReportName("Test Report");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("Douglas Website Test", "Testing Douglas website functionalities");
    }

    @Test
    public void testDouglasWebsite() {
        test.log(Status.INFO, "Navigating to Douglas website");
        driver.get("https://www.douglas.de/");
        driver.manage().window().maximize();
        waitForPageLoad();

        String expectedTitle = "Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS";
        wait.until(ExpectedConditions.titleIs(expectedTitle));

        if (driver.getTitle().equals(expectedTitle)) {
            test.log(Status.PASS, "Page title is correct: " + expectedTitle);
            handleCookiesSection();
            handlePerfumeSection();
            firstSearchScenario();
            secondSearchScenario();
            pause(5000);  // Just to see the result before quitting, not recommended in real tests
        } else {
            // test.log(Status.FAIL, "Page title is incorrect. Actual title: " + driver.getTitle());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void handleCookiesSection() {
        pause(1000);
        SearchContext shadow = driver.findElement(By.id("usercentrics-root")).getShadowRoot();
        pause(1000);
        shadow.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz")).click();
        pause(1000);
    }

    private void handlePerfumeSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='PARFUM']")));
        driver.findElement(By.xpath("//a[normalize-space()='PARFUM']")).click();
        wait.until(ExpectedConditions.titleIs("Parfüm & Düfte ✔️ online kaufen » für Sie & Ihn | DOUGLAS"));
        pause(5000);
        moveMouse();
    }

    private void moveMouse() {
        PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");

        Sequence actions = new Sequence(mouse, 0)
                .addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
                .addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));

        ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
    }

    private void firstSearchScenario() {
        searchByMarke();
        searchByProduktart();
        pause(5000);  // Just to see the result
        clearSearchBar();
    }

    private void secondSearchScenario() {
        searchByHighlights();
        pause(5000);  // Just to see the result
        clearSearchBar();
    }

    private void searchByMarke() {
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Marke']")).click();
    }

    private void searchByProduktart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Produktart')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Produktart')]")).click();
        pause(5000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Produktart suchen']")));
        driver.findElement(By.xpath("//input[@placeholder='Produktart suchen']")).click();
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Produktart']")).click();
    }

    private void searchByHighlights() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Highlights')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Highlights')]")).click();
        pause(5000);
        
        new Actions(driver)
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//div[text()='Highlights']")).click();
    }

    private void clearSearchBar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")));
        driver.findElement(By.xpath("//*[contains(text(), 'Alle Filter löschen')]")).click();
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
