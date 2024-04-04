package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class JavaHashGroupTest extends BaseTest {

    @Test
    public void testMainPageTitle() {

        final String expectedTitle = "Swag Labs";

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().get("https://www.saucedemo.com");

        WebElement loginField = getDriver().findElement(By.xpath("//input[@id='user-name']"));
        WebElement passField = getDriver().findElement(By.xpath("//input[@id='password']"));
        WebElement submitButton = getDriver().findElement(By.xpath("//input[@id='login-button']"));

        loginField.sendKeys("standard_user");
        passField.sendKeys("secret_sauce");
        submitButton.click();

        String actualTitle = getDriver().findElement(By.xpath("//div[contains(text(),'Swag Labs')]")).getText();

        Assert.assertEquals(actualTitle,expectedTitle);
    }

    @Test
    public void testBuyingBlackShirt() {

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().get("https://www.saucedemo.com");

        WebElement loginField = getDriver().findElement(By.xpath("//input[@id='user-name']"));
        WebElement passField = getDriver().findElement(By.xpath("//input[@id='password']"));
        WebElement submitButton = getDriver().findElement(By.xpath("//input[@id='login-button']"));

        loginField.sendKeys("standard_user");
        passField.sendKeys("secret_sauce");
        submitButton.click();

        WebElement tshirtTitle = getDriver().findElement(By.xpath("//div[@class='inventory_item_name '][contains(text(),'Sauce Labs Bolt T-Shirt')]"));
        tshirtTitle.click();

        WebElement tshirtPageTitle = getDriver().findElement(By.xpath("//div[@class='inventory_details_name large_size'][contains(text(),'Sauce Labs Bolt T-Shirt')]"));
        Assert.assertTrue(tshirtPageTitle.isDisplayed());

        WebElement addToCartButton = getDriver().findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
        addToCartButton.click();

        WebElement goToCartButton = getDriver().findElement(By.xpath("//a[@class='shopping_cart_link']"));
        goToCartButton.click();

        WebElement descriptionOfItemInCart = getDriver().findElement(By.xpath("//div[@class='inventory_item_desc']"));
        Assert.assertTrue(descriptionOfItemInCart.isDisplayed());

        WebElement checkoutButton = getDriver().findElement(By.xpath("//button[@id='checkout']"));
        checkoutButton.click();

        WebElement firstNameField = getDriver().findElement(By.xpath("//input[@placeholder='First Name']"));
        WebElement lastNameField = getDriver().findElement(By.xpath("//input[@placeholder='Last Name']"));
        WebElement postCodeField = getDriver().findElement(By.xpath("//input[@placeholder='Zip/Postal Code']"));

        firstNameField.sendKeys("David");
        lastNameField.sendKeys("Adams");
        postCodeField.sendKeys("777777");

        WebElement continueButton = getDriver().findElement(By.xpath("//input[@id='continue']"));
        continueButton.click();

        WebElement checkoutPageTitle = getDriver().findElement(By.xpath("//span[contains(text(),'Checkout: Overview')]"));
        Assert.assertTrue(checkoutPageTitle.isDisplayed());

        WebElement finishButton = getDriver().findElement(By.xpath("//button[@id='finish']"));
        finishButton.click();

        WebElement successOrderSign = getDriver().findElement(By.xpath("//h2[contains(text(),'Thank you for your order!')]"));
        Assert.assertTrue(successOrderSign.isDisplayed());
    }

    private static final String BAD_USERNAME = "username@gmail.com";
    private static final String BAD_PASSWORD = "Password123";
    @Test
    public void testBadLogin() throws InterruptedException {
        getDriver().get("https://www.sofa.com/gb");

        WebElement loginButton = getDriver().findElement(By.xpath("//a[@href=\"/gb/login\"]"));
        loginButton.click();
        Thread.sleep(500);

        WebElement inputEmail = getDriver().findElement(By.id("j_username"));
        inputEmail.sendKeys(BAD_USERNAME);
        WebElement inputPassword = getDriver().findElement(By.id("j_password"));
        inputPassword.sendKeys(BAD_PASSWORD);
        getDriver().findElement(By.id("loginBtn")).click();
        Thread.sleep(600);

        WebElement errorText = getDriver().findElement(By.xpath("//div[@class=\"alert alert-danger alert-dismissable\"]"));

        Assert.assertEquals(errorText.getText(), "×\n" + "Your username or password was incorrect.");
    }
}