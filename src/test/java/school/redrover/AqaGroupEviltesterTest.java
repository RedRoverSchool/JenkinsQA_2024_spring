package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AqaGroupEviltesterTest extends AqaGroupBaseTest {

    private static final String BUTTONS_URL = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
    private static final String ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
    private static final String FAKE_ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/fake-alert-test.html";

    @Test
    public void testDisabledDynamicButtons() {
        getDriver().get(BUTTONS_URL);

        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

//        TODO make it prettier
        Assert.assertTrue(getWait15().until(
                        ExpectedConditions.textToBePresentInElementLocated(
                                By.id("buttonmessage"),
                                "All Buttons Clicked")),
                "Text \"All Buttons Clicked\" not found");
    }

    @Test
    public void testSimpleAlert() {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id("alertexamples")).click();

        getWait15().until(ExpectedConditions.alertIsPresent()).accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("alertexplanation")).getText(),
                "You triggered and handled the alert dialog");
    }

    @Test
    public void testAcceptConfirmAlert() {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id("confirmexample")).click();

        getWait15().until(ExpectedConditions.alertIsPresent()).accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmexplanation")).getText(),
                "You clicked OK, confirm returned true.");
    }

    @Test
    public void testDismissConfirmAlert() {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id("confirmexample")).click();

        getWait15().until(ExpectedConditions.alertIsPresent()).dismiss();

        Assert.assertEquals(
                getDriver().findElement(By.id("confirmexplanation")).getText(),
                "You clicked Cancel, confirm returned false.");
    }

    @Test
    public void testAcceptPromptAlert() {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id("promptexample")).click();

        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());

        final String myKeys = "some random input";
        alert.sendKeys(myKeys);
        alert.accept();

        Assert.assertEquals(
                getDriver().findElement(By.id("promptexplanation")).getText(),
                String.format("You clicked OK. 'prompt' returned %s", myKeys));
    }

    @Test
    public void testDismissPromptAlert() {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id("promptexample")).click();

        getWait15().until(ExpectedConditions.alertIsPresent()).dismiss();

        Assert.assertEquals(
                getDriver().findElement(By.id("promptexplanation")).getText(),
                "You clicked Cancel. 'prompt' returned null");
    }

    @Test
    public void testExpandingDivWithClickableLink() {
        getDriver().get("https://testpages.eviltester.com/styled/expandingdiv.html");

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.className("expand")))
                .pause(500)
                .moveToElement(getDriver().findElement(By.cssSelector(".expand p a")))
                .click()
                .pause(500)
                .perform();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("expandeddiv"), "Unexpected URL.");
    }

    @Test
    public void fakeAlertTest() {
//        TODO getDriver()
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
        //        TODO getDriver()
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
        //        TODO getDriver()
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

        ((ChromeDriver) getDriver()).executeCdpCommand("Network.setUserAgentOverride", Map.of("userAgent", pixelSeven));

        getDriver().get("https://testpages.eviltester.com/styled/redirect/user-agent-redirect-test");

        Assert.assertTrue(
                getDriver().findElement(By.className("explanation")).getText().startsWith("You probably"),
                "UserAgent change failed");
    }
}

