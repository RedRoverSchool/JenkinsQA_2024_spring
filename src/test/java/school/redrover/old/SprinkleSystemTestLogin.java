package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.Console;
import java.time.Duration;

@Ignore
public class SprinkleSystemTestLogin {
    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://project-atlas.dev/");
        WebElement button = driver.findElement(By.xpath("//a[@href = '/dashboard']"));
        button.click();

        WebElement text = driver.findElement(By.name("email"));
        text.sendKeys("userFirst@yopmail.com");

        text = driver.findElement(By.name("password"));
        text.sendKeys("User@1");

        button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='status']")));
        String resultText = element.getText();
        Assert.assertEquals(resultText, "server_errors.UserNotFound");

        driver.quit();
    }
}
