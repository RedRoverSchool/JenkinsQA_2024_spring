package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class JavaHashGroupTest extends BaseTest {

    @Test
    public void mainPageTitleTest() {

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