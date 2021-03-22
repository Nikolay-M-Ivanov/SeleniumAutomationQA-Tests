package com.selenium.course.base;

import com.selenium.course.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestUtil{
    public WebDriver driver;
    private String url;
    private int implicitWait;
    private String browser;

    @BeforeSuite
    public void readConfigProperties(){
        try (
            FileInputStream configFile = new FileInputStream("src/test/resources/config.properties")) {
                Properties config = new Properties();
                config.load(configFile);
                url = config.getProperty("urlAddress");
                implicitWait = Integer.parseInt(config.getProperty("implicitWait"));
                // browser to be taken from property file
            browser = config.getProperty("browser");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void setUp() {
        setupBrowsweDriver();
        loadUrl();
        /*System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://saucedemo.com/");*/
    }

    private void loadUrl() {
        driver.get(url);
    }

    private void setupBrowserDriver(){
        switch (browser){
            case "chrome":
                driver = DriverFactory.getChromeDriver(implicitWait);
                break;
            case "firefox":
                driver = DriverFactory.getFirefoxDriver(implicitWait);
            default:
                throw new IllegalStateException("Unsuported browser");
        }
    }

    private void setupBrowsweDriver() {
        driver = DriverFactory.getFirefoxDriver(implicitWait);
        //driver = DriverFactory.getChromeDriver(browser);
        //HW chrome implementation + switch case
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
