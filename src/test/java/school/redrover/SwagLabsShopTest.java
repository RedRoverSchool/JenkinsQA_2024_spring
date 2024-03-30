package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SwagLabsShopTest {
    @Test
    public void testMyFirstTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement usernameField = driver.findElement(By.name("user-name"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        String url = driver.getCurrentUrl();
        WebElement logo = driver.findElement(By.className("app_logo"));
        String value = logo.getText();
        Assert.assertEquals(url, "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(value, "Swag Labs");

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));
        addToCartButton.click();

        WebElement removeFromCartButton = driver.findElement(By.id("remove-test.allthethings()-t-shirt-(red)"));
        String removeFromCartButtonID = removeFromCartButton.getAttribute("id");
        Assert.assertEquals(removeFromCartButtonID, "remove-test.allthethings()-t-shirt-(red)");

        WebElement shoppingCartBage = driver.findElement(By.className("shopping_cart_badge"));
        String counter = shoppingCartBage.getText();
        Assert.assertEquals(counter, "1");

        WebElement shopCartLink = driver.findElement(By.className("shopping_cart_link"));
        shopCartLink.click();

        WebElement cartItem = driver.findElement(By.xpath("//a[@id='item_3_title_link']/div[@class = 'inventory_item_name']"));
        String cartItemName = cartItem.getText();
        Assert.assertEquals(cartItemName, "Test.allTheThings() T-Shirt (Red)");

        driver.quit();
    }
}
