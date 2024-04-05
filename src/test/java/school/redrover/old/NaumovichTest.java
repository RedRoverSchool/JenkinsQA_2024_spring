package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class NaumovichTest {

    @Test
    public void testSauceDemo() {
        String expectedResult = "https://www.saucedemo.com/inventory.html";

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");
        WebElement name = driver.findElement(By.xpath("//input[@id='user-name']"));
        name.sendKeys("standard_user");
        WebElement passcode = driver.findElement(By.xpath("//input[@id='password']"));
        passcode.sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();

    }
}
