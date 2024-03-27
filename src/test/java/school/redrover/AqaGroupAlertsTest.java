package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupAlertsTest extends AqaGroupBaseTest {
    private static final String URL = "https://demoqa.com/alerts";

    @Test
    public void testAlert() {
        driver.get(URL);

        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();
        Assert.assertTrue(
                driver.findElement(By.id("alertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testWaitingAlert() {
        driver.get(URL);

        driver.findElement(By.id("timerAlertButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertTrue(
                driver.findElement(By.id("timerAlertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testAcceptConfirmAlert() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().accept();

        Assert.assertEquals(
                driver.findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "alert is not accepted");
    }

    @Test
    public void testDismissConfirmAlert() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();

        Assert.assertEquals(
                driver.findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "alert is not dismissed");
    }

    @Test
    public void testPromptAlert() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("promtButton")).click();
        Alert alert = driver.switchTo().alert();
        alert.sendKeys("Irina");
        alert.accept();

        Assert.assertEquals(
                driver.findElement(By.id("promptResult")).getText(),
                "You entered Irina",
                "unexpected result");
    }
}
