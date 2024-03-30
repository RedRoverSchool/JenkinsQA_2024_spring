package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceDemoKTest {
    @Test
    public void testTools() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement text = driver.findElement(By.id("user-name"));
        text.sendKeys("standard_user");

        WebElement text1 = driver.findElement(By.id("password"));
        text1.sendKeys("secret_sauce");

        WebElement button = driver.findElement(By.id("login-button"));
        button.click();

        Thread.sleep(1000);

        WebElement link = driver.findElement(By.className("app_logo"));
        String resultText = link.getText();

        Assert.assertEquals(resultText, "Swag Labs");
        driver.quit();
    }
}

