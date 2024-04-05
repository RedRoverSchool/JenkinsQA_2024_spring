package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class LoginNonexistentUserTest {
    @Test
    public void loginNonexistentUser() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("testUser");

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("testPassword");

        WebElement submitButton = driver.findElement(By.cssSelector("input.button"));
        submitButton.click();

        WebElement message = driver.findElement(By.cssSelector("p.error"));
        String actualErrorMessage = message.getText();
        Assert.assertEquals(actualErrorMessage, "The username and password could not be verified.");

        driver.quit();
    }
}

