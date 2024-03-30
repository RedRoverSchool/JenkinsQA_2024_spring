package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupAlertsTest extends AqaGroupBaseTest {
    private static final String URL = "https://demoqa.com/alerts";

    @Test
    public void testAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        getDriver().findElement(By.id("alertButton")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertTrue(
                getDriver().findElement(By.id("alertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testWaitingAlert() {
        getDriver().get(URL);

        getDriver().findElement(By.id("timerAlertButton")).click();
        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertTrue(
                getDriver().findElement(By.id("timerAlertButton")).isDisplayed(),
                "alert is not accepted");
    }

    @Test
    public void testAcceptConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "alert is not accepted");
    }

    @Test
    public void testDismissConfirmAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("confirmButton"))).click();
        getDriver().switchTo().alert().dismiss();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "alert is not dismissed");
    }

    @Test
    public void testPromptAlert() {
        getDriver().get(URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(15000));

        scrollIntoView(getDriver().findElement(By.id("promtButton"))).click();
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys("Irina");
        alert.accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("promptResult")).getText(),
                "You entered Irina",
                "unexpected result");
    }
}