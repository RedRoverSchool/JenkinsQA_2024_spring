package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumLoginTest {
    @Test
    public void testLogin() {

        WebDriver driver = new ChromeDriver(); // Navigate to URL
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        WebElement loginLogo = driver.findElement(By.className("login_logo")); // Check logo
        String loginLogoText = loginLogo.getText();
        Assert.assertEquals("Swag Labs", loginLogoText);

        WebElement usernameField = driver.findElement(By.id("user-name")); // Login
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement item1 = driver.findElement(By.linkText("Sauce Labs Backpack")); // Add item to cart
        item1.click();

        WebElement addToCartButton1 = driver.findElement(By.xpath("//*[@id=\"add-to-cart\"]"));
        addToCartButton1.click();

        WebElement backToProductsButton = driver.findElement(By.id("back-to-products"));
        backToProductsButton.click();

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        cartBadge.click();

        WebElement checkoutButton = driver.findElement(By.name("checkout")); // Checkout
        checkoutButton.click();

        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.sendKeys("Terrance");

        WebElement lastNameField = driver.findElement(By.id("last-name"));
        lastNameField.sendKeys("Hobbs");

        WebElement postalCodeField = driver.findElement(By.name("postalCode"));
        postalCodeField.sendKeys("666666");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        WebElement totalPrice = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]"));
        String value = totalPrice.getText();
        Assert.assertEquals("Total: $32.39", value);

        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();

        WebElement completeHeader = driver.findElement(By.className("complete-header"));
        String headerText = completeHeader.getText();
        Assert.assertEquals("Thank you for your order!", headerText);

        WebElement backHomeButton = driver.findElement(By.name("back-to-products"));
        backHomeButton.click();

        WebElement burgerMenuButton = driver.findElement(By.id("react-burger-menu-btn")); // Logout
        burgerMenuButton.click();

        WebElement logoutSidebarLink = driver.findElement(By.id("logout_sidebar_link"));
        logoutSidebarLink.click();

        driver.quit();
    }
}