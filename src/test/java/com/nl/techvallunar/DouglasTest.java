package com.nl.techvallunar;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.nl.techvallunar.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DouglasTest {
    
    private WebDriver driver;
    HomePage homePage;
    ExtentTest test;
    ExtentReports extent;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Selenium Test Report");
        htmlReporter.config().setReportName("Test Report");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("Douglas Website Test", "Testing Douglas website functionalities");
    }

    @Test void testHomePage() {
        homePage.navigateToHomePage("https://www.douglas.de/");
        test.info("Home page loaded!");
        homePage.acceptCookies();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if(extent != null){
            extent.flush();
        }

    }
}
