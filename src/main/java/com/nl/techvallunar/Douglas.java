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



import java.time.Duration;
import java.util.Collections;

public class Douglas {

    private static final String INPUT_PLACEHOLDER_MARKE_SUCHEN = "//input[@placeholder='Marke suchen']";
    private static final String PRODUKTART_SUCHEN_XPATH = "//input[@placeholder='Produktart suchen']";
    private static final String PRODUKTCART_XPATH = "//div[contains(text(),'Produktart')]";
    private static final String MARKE_XPATH = "//div[contains(text(),'Marke')]";
    private static final String PARFUM_XPATH = "//a[normalize-space()='PARFUM']";
    private static final String HIGHT_LIGHTS_XPATH = "//div[contains(text(),'Highlights')]";
    private static final String FILTER_BAR_XPATH = "//*[contains(text(), 'Alle Filter löschen')]";
    private static final String EXPECTED_TITLE = "Online-Parfümerie ✔️ Parfum & Kosmetik kaufen | DOUGLAS";
    private static WebDriver driver;
    private static WebDriverWait wait;

    static ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
    static ExtentReports extent = new ExtentReports();
    static ExtentTest test = extent.createTest("Douglas Website Test", "Testing Douglas website functionalities");

    public static void main(String[] args) {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Selenium Test Report");
        htmlReporter.config().setReportName("Test Report");
        extent.attachReporter(htmlReporter);


        try {
            driver.get("https://www.douglas.de/");
            waitForPageLoad();
            test.log(Status.INFO, "Page loaded!");
            driver.manage().window().maximize();
            wait.until(ExpectedConditions.titleIs(EXPECTED_TITLE));
            if (driver.getTitle().equals(EXPECTED_TITLE)) {
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
            if (extent != null) {
                extent.flush();
            }
        }
    }

    private static void handleCookiesSection() {
        pause(1000);
        SearchContext shadow = driver.findElement(By.id("usercentrics-root")).getShadowRoot();
        pause(1000);
        shadow.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz")).click();
        pause(1000);
        test.log(Status.INFO, "Cookies handled");
    }

    private static void handlePerfumeSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PARFUM_XPATH)));
        driver.findElement(By.xpath(PARFUM_XPATH)).click();
        wait.until(ExpectedConditions.titleIs("Parfüm & Düfte ✔️ online kaufen » für Sie & Ihn | DOUGLAS"));
        pause(5000);
        moveMouse();
        test.log(Status.INFO, "Perfome section Done");
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
        test.log(Status.INFO, "First search scenario done!");
    }

    private static void secondSearchScenario() {
        searchByHighlights();
        pause(5000);  // Just to see the result
        clearSearchBar();
        test.log(Status.INFO, "Second search scenario done!");
    }

    private static void searchByMarke() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MARKE_XPATH)));
        driver.findElement(By.xpath(MARKE_XPATH)).click();
        pause(5000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INPUT_PLACEHOLDER_MARKE_SUCHEN)));
        driver.findElement(By.xpath(INPUT_PLACEHOLDER_MARKE_SUCHEN)).click();
        new Actions(driver)
            .sendKeys("4711")
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_BAR_XPATH)));
        driver.findElement(By.xpath("//div[text()='Marke']")).click();
    }

    private static void searchByProduktart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUKTCART_XPATH)));
        driver.findElement(By.xpath(PRODUKTCART_XPATH)).click();
        pause(5000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUKTART_SUCHEN_XPATH)));
        driver.findElement(By.xpath(PRODUKTART_SUCHEN_XPATH)).click();
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
