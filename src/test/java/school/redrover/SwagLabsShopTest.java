package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class SwagLabsShopTest extends BaseTest {
    @Test
    public void testLogin() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(getDriver().findElement(By.className("app_logo")).getText(), "Swag Labs");
    }

    @Test
    public void testAddToCart() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.className("shopping_cart_link")).click();

        Assert.assertEquals(getDriver().findElement(By.className("shopping_cart_badge")).getText(), "1");
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@id='item_3_title_link']/div[@class = 'inventory_item_name']")).getText(), "Test.allTheThings() T-Shirt (Red)");
    }

    @Test
    public void testRemoveFromCart() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        getDriver().findElement(By.name("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.name("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.id("remove-test.allthethings()-t-shirt-(red)")).click();
        getDriver().findElement(By.className("shopping_cart_link")).click();

        Assert.assertTrue(getDriver().findElements(By.className("shopping_cart_badge")).isEmpty());
        Assert.assertTrue(getDriver().findElements(By.className("cart_item")).isEmpty());
    }
}