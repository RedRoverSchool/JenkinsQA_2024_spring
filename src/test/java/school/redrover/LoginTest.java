package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    @Test

    public void testLogin(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://practice.expandtesting.com/login");

        WebElement loginField = driver.findElement(By.name("username"));
        loginField.sendKeys("Anna");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123456789");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebElement loginError = driver.findElement(By.xpath("//div[contains(@class,'alert alert-danger')]"));
        String value = loginError.getText();

        Assert.assertEquals(value,"Your username is invalid!");

        driver.quit();

    }
}
