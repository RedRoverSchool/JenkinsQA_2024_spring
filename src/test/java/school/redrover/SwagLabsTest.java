package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SwagLabsTest {
    @Test
    public void testSeleniumPractice() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement title = driver.findElement (By.className("login_logo"));
        Assert.assertEquals ("Swag Labs", title.getText());


        WebElement loginBtn = driver.findElement(By.id("user-name"));
        WebElement passwordBtn = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("login-button"));


        loginBtn.sendKeys("standard_user");
        passwordBtn.sendKeys("secret_sauce");
        submitBtn.click();

        Thread.sleep(1000);

        WebElement title2 = driver.findElement (By.className("app_logo"));
        Assert.assertEquals ("Swag Labs", title2.getText());

        driver.quit();
    }
}
