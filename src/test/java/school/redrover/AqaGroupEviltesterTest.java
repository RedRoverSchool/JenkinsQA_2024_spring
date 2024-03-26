package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

public class AqaGroupEviltesterTest extends AqaGroupBaseTest {
    private static final String BUTTONS_URL = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
    private static final String ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
    private static final String FAKE_ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/fake-alert-test.html";

    @Test
    public void testDisabledDynamicButtonsVersionOne() {
        driver.get(BUTTONS_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

        Boolean found = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("buttonmessage"),
                "All Buttons Clicked"));
        Assert.assertTrue(found, "Text \"All Buttons Clicked\" not found");


    }

    @Test
    public void testDisabledDynamicButtonsVersionTwo() {
        driver.get(BUTTONS_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

        WebElement message = driver.findElement(By.id("buttonmessage"));
        wait.until(ExpectedConditions.textToBePresentInElement(message, "All Buttons Clicked"));

        Assert.assertEquals(message.getText(), "All Buttons Clicked");

    }

    @Test
    public void testSimpleAlert() {
        driver.get(ALERTS_URL);

        driver.findElement(By.id("alertexamples")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertEquals(
                driver.findElement(By.id("alertexplanation")).getText(),
                "You triggered and handled the alert dialog");
    }

    @Test
    public void testAcceptConfirmAlert() {
        driver.get(ALERTS_URL);

        driver.findElement(By.id("confirmexample")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        Assert.assertEquals(
                driver.findElement(By.id("confirmexplanation")).getText(),
                "You clicked OK, confirm returned true.");
    }

    @Test
    public void testDismissConfirmAlert() {
        driver.get(ALERTS_URL);

        driver.findElement(By.id("confirmexample")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        Assert.assertEquals(
                driver.findElement(By.id("confirmexplanation")).getText(),
                "You clicked Cancel, confirm returned false.");
    }

    @Test
    public void testAcceptPromptAlert() {
        driver.get(ALERTS_URL);

        driver.findElement(By.id("promptexample")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String myKeys = "some random input";
        alert.sendKeys(myKeys);
        alert.accept();

        Assert.assertEquals(
                driver.findElement(By.id("promptexplanation")).getText(),
                String.format("You clicked OK. 'prompt' returned %s", myKeys));
    }

    @Test
    public void testDismissPromptAlert() {
        driver.get(ALERTS_URL);

        driver.findElement(By.id("promptexample")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        Assert.assertEquals(
                driver.findElement(By.id("promptexplanation")).getText(),
                "You clicked Cancel. 'prompt' returned null");
    }

    @Test
    public void testExpandingDivWithClickableLink() {
        driver.get("https://testpages.eviltester.com/styled/expandingdiv.html");
        new Actions(driver)
                .moveToElement(driver.findElement(By.className("expand")))
                .pause(500)
                .moveToElement(driver.findElement(By.cssSelector(".expand p a")))
                .click()
                .pause(500)
                .perform();

        Assert.assertTrue(driver.getCurrentUrl().contains("expandeddiv"), "Unexpected URL.");
    }

    @Test
    public void fakeAlertTest() {
        driver.get(FAKE_ALERTS_URL);

        driver.findElement(By.id("fakealert")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.id("dialog-ok")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake alert box is active");
    }

    @Test
    public void fakeModalAlertCloseOkTest() {
        driver.get(FAKE_ALERTS_URL);

        driver.findElement(By.id("modaldialog")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.id("dialog-ok")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake modal alert box is active");
    }

    @Test
    public void fakeModalAlertCloseBackgroundTest() {
        driver.get(FAKE_ALERTS_URL);

        driver.findElement(By.id("modaldialog")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.cssSelector(".faded-background.active")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake modal alert box is active");
    }

    @Test
    public void testCDPUserAgentChange() {
        final String pixelSeven = "Mozilla/5.0 (Linux; Android 13; Pixel 7) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36";
        ((ChromeDriver) driver).executeCdpCommand(
                "Network.setUserAgentOverride", Map.of("userAgent", pixelSeven));

        driver.get("https://testpages.eviltester.com/styled/redirect/user-agent-redirect-test");

        Assert.assertTrue(
                driver.findElement(By.className("explanation")).getText().startsWith("You probably"),
                "UserAgent change failed");
    }
}

