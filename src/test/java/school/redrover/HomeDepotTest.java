package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class HomeDepotTest extends BaseTest {
    @Test
    public void loginFailed() {
        WebDriver driver = getDriver();
        driver.get("https://www.homedepot.ca/en/home.html");
        driver.findElement(By.cssSelector("[data-title*='Account / Sign In']")).click();

        //Waits for Login screen appearance
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='acl-display--flex acl-flex--column acl-mt--x-large acl-mx--medium acl-text-size--small']")));

        driver.findElement(By.cssSelector("[type*='email']")).sendKeys("atest@gmail.com");
        driver.findElement(By.cssSelector("[type*='password']")).sendKeys("test");

        driver.findElement(By.cssSelector("[type='submit']")).click();

        //Waits until error message screen appearance
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='acl-alert__header acl-display--flex acl-align-items--center']")));

        //Checks if the error message is correct
        String actualErrorMessage = driver.findElement(By.cssSelector("[class*='acl-alert__header acl-display--flex acl-align-items--center']")).getText();
        String expectedErrorMessage = "Please confirm the email address entered is correct. If it is, click Forgot Password to reset the password on your account. If you are still unable to log in, please contact us.";

        Assert.assertEquals(expectedErrorMessage,actualErrorMessage);
    }
}
