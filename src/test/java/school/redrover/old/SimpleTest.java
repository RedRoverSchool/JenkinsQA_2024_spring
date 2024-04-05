package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class SimpleTest {
    @Test
    public void testTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.saucedemo.com/");


        WebElement textBox = driver.findElement(By.name("user-name"));
        textBox.sendKeys("Selenium");

        WebElement login = driver.findElement(By.id("login-button"));
        login.click();

        WebElement message = driver.findElement(By.cssSelector("h3"));
        String value = message.getText();
        Assert.assertEquals("Epic sadface: Password is required", value);

        driver.quit();
    }
}
