package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ShopSwagTest {
    @Test
    public void testSaucedemo() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://www.saucedemo.com/");

        WebElement textUsername = driver.findElement(By.id("user-name"));
        textUsername.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();

        WebElement addToCart = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCart.click();

        WebElement remove = driver.findElement(By.id("remove-sauce-labs-backpack"));
        String value = remove.getText();
        Assert.assertEquals(value, "Remove");

        WebElement shoppingCartlink = driver.findElement(By.id("shopping_cart_container"));
        shoppingCartlink.click();

        WebElement checkout = driver.findElement(By.id("checkout"));
        checkout.click();

        WebElement textFirstName = driver.findElement(By.id("first-name"));
        textFirstName.sendKeys("First Name");

        WebElement textLastName = driver.findElement(By.id("last-name"));
        textLastName.sendKeys("Last Name");

        WebElement textpostalCode = driver.findElement(By.id("postal-code"));
        textpostalCode.sendKeys("123");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        WebElement finishButton = driver.findElement(By.name("finish"));
        finishButton.click();

        WebElement backButton = driver.findElement(By.name("back-to-products"));
        backButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        driver.quit();
    }
}
