package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupEviltesterTest {
    @Test
    public void testDisabledDynamicButtonsVersionOne() {
        String link = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

            Boolean found = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonmessage"), "All Buttons Clicked"));
            Assert.assertTrue(found, "Text \"All Buttons Clicked\" not found");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDisabledDynamicButtonsVersionTwo() {
        String link = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

            WebElement message = driver.findElement(By.id("buttonmessage"));
            wait.until(ExpectedConditions.textToBePresentInElement(message, "All Buttons Clicked"));

            Assert.assertEquals(message.getText(), "All Buttons Clicked");
        } finally {
            driver.quit();
        }
    }
}

