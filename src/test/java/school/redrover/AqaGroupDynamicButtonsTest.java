package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupDynamicButtonsTest {

    WebDriver driver;

    @AfterMethod
    public void close() {
        if (driver != null) driver.quit();
    }

    @Test
    public void simpleDynamicButtonsTest() {
        driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/dynamic-buttons-simple.html");

        driver.findElement(By.id("button00")).click();
        driver.findElement(By.id("button01")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button02")));
        driver.findElement(By.id("button02")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button03")));
        driver.findElement(By.id("button03")).click();

        Assert.assertEquals(driver.findElement(By.id("buttonmessage")).getText(), "All Buttons Clicked", "Not All Buttons Clicked");
    }

    @Test
    public void XHTTPMessagesTest() {
        driver = new ChromeDriver();
        driver.get("https://testpages.eviltester.com/styled/sync/xhttp-messages.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("messagesstatus")), "Message Count: 0 : 0"));

        Assert.assertEquals(driver.findElement(By.id("messagesstatus")).getText(), "Message Count: 0 : 0", "message count is not zero");
    }
}
