package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.util.List;

public class SauceEditCartTest {
    WebDriver driver = new ChromeDriver();
    String allItemsInTheCart = "//div[@class='cart_item']";
    String cartPage = "//a[@class='shopping_cart_link']";
    public void login(){
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
    }
    @Test
    public void testAddItemToTheCard(){
        boolean expectedResult = true;
        login();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']")).click();
        driver.findElement(By.xpath(cartPage)).click();
        boolean actualResult = driver.findElement(By.linkText("Sauce Labs Bike Light")).isDisplayed();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testRemoveItemFromTheCart(){
        //Preconditions
        login();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath(cartPage)).click();
        driver.findElement(By.linkText("Sauce Labs Backpack")).isDisplayed();
        List<WebElement> items = driver.findElements(By.xpath(allItemsInTheCart));
        int amountItemsBeforeAction = items.size();
        //Test
        driver.findElement(By.xpath("//button[@id='remove-sauce-labs-backpack']")).click();
        items = driver.findElements(By.xpath(allItemsInTheCart));
        Assert.assertNotEquals(amountItemsBeforeAction, items.size());
    }

    @AfterMethod
    public void clear(){}
    public void quit(){driver.quit();
    }
}