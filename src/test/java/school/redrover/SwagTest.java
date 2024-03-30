package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SwagTest {
    @Test
    public void testSwagLogin() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/?ref=hackernoon.com");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        usernameInput.sendKeys("standard_user");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        WebElement shopCartButton = driver.findElement(By.id("shopping_cart_container"));

        Assert.assertTrue(shopCartButton.isDisplayed(), "Shop cart doesn't displayed");

        driver.quit();
        }
}
