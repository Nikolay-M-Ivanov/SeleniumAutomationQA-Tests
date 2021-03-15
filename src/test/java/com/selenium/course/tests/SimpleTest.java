package com.selenium.course.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class SimpleTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void executeSimpleTest() {
        driver.get("http://saucedemo.com");

        //намираме елемент userInput и въвеждаме потребителско
        WebElement userNameInput = driver.findElement(By.id("user-name"));
        userNameInput.sendKeys("standard_user");
        //намираме ел парола и въвеждаме парола secret_sauce
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[value=LOGIN]"));
        loginButton.click();

        WebElement selectElement = driver.findElement(By.className("product_sort_container"));
        Select list = new Select(selectElement);
        list.selectByValue("lohi");
        list.selectByVisibleText("Name (Z to A)");


        WebElement addToCart = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']//ancestor::div[@class='inventory_item']"));
        addToCart.click();

        WebElement badge = driver.findElement(By.cssSelector(".shopping_cart_badge"));
        assertEquals(badge.getText(), "1");



    }

    @AfterTest
    public void tearDown(){
    //    driver.close();  //само затваря браузъра
      //  driver.quit();  //спира самия селениум, разваля връзката
    }
}
