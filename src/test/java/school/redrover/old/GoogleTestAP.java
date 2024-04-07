package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class GoogleTestAP {
    @Test
    public void GoogleTestAP() {
        String expectedResult = "https://www.saucedemo.com/inventory.html";

        WebDriver driver = new FirefoxDriver();

        driver.get("https://www.saucedemo.com/");
        WebElement name = driver.findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("not_standard_user");
        WebElement passcode = driver.findElement(By.xpath("//input[@id='password']"));
        passcode.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();

    }
}
