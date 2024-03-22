package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JavAngryTest {

    @Test
    void testPriceCheckMainPageShoppingPage() {
        WebDriver webDriver = new ChromeDriver();
        final String login = "standard_user";
        final String password = "secret_sauce";
        final String itemToBuy = "Sauce Labs Bike Light".toLowerCase().replaceAll(" ", "-");
        webDriver.get("https://www.saucedemo.com/");
        webDriver.findElement(By.id("user-name")).sendKeys(login);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.id("login-button")).click();
        String expectedResult = webDriver.findElement(
                By.xpath("//button[contains(@name, '" + itemToBuy + "')]/../div")).getText();
        webDriver.findElement(By.xpath("//button[@id='add-to-cart-" + itemToBuy + "']")).click();
        webDriver.findElement(By.className("shopping_cart_link")).click();
        String actualResult = webDriver.findElement(
                By.xpath("//button[contains(@name, '" + itemToBuy + "')]/../div")).getText();

        Assert.assertEquals(actualResult, expectedResult);
        webDriver.quit();
    }
}
