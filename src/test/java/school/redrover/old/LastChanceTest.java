package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class LastChanceTest {

    @Test

    public void  testCurrencyCalculator(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://futureboy.us/fsp/dollar.fsp");

        WebElement dollarQuantity = driver.findElement(By.xpath("//input[@name='quantity']"));
        dollarQuantity.click();
        dollarQuantity.sendKeys("100");

        WebElement currency = driver.findElement(By.xpath("//select[@name ='currency']"));
        currency.click();

        WebElement selectCurrency = driver.findElement(By.xpath("//option[contains(text(),'mark')]"));
        selectCurrency.click();

        WebElement selectYear = driver.findElement(By.name("fromYear"));
        selectYear.click();
        selectYear.sendKeys("1900");

        WebElement submitButton = driver.findElement(By.xpath("//input[@type='SUBMIT']"));
        submitButton.click();

        WebElement resultField = driver.findElement(By.cssSelector(".back"));
        String actual = resultField.getText();
        String expected = "100 mark in 1900 had the same buying power as";
        Assert.assertTrue(actual.contains(expected),"Text is not the same");

        driver.quit();
    }
}
