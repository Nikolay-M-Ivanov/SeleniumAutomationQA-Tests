package com.selenium.course.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //implicit безусловно
    }

    @Test()
    public void executeSimpleTest(String userName, String password) {
        driver.get("http://saucedemo.com/");

        //намираме елемент userInput и въвеждаме потребителско
        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.sendKeys("standard_user");
        //намираме ел парола и въвеждаме парола secret_sauce
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[value=LOGIN]"));
        loginButton.click();

        //Explicit wait - условно (чакаме някакво условие)
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //изключваме преди условното
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("product_label1")));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //включваме отново

        //Fluent wait
        FluentWait fluentWait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(20)) //докога ще проверява условието
                .pollingEvery(Duration.ofSeconds(2)) //през колко сек ще проверява условието
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.invisibilityOf(loginButton));

        //Advanced
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", loginButton);
        js.executeScript("arguments[0].click()", loginButton);
    }

    @AfterTest
    public void tearDown() {
        //    driver.close();  //само затваря браузъра
        driver.quit();  //спира самия селениум, разваля връзката
    }

}
