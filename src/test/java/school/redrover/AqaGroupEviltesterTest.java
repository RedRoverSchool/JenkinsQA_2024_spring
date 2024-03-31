package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

public class AqaGroupEviltesterTest extends AqaGroupBaseTest {

    private static final String BUTTONS_URL = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
    private static final String ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
    private static final String FAKE_ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/fake-alert-test.html";
    private static final String ALERT_DISPLAYED = "fake alert box is displayed";
    private static final By OPEN_MODAL_DIALOG_BUTTON = By.id("modaldialog");
    private static final By MODAL_DIALOG_OK_BUTTON = By.id("dialog-ok");
    private static final By MODAL_DIALOG_TEXT = By.id("dialog-text");

    @Test
    public void testSimpleDynamicButtons() {
        getDriver().get("https://testpages.eviltester.com/styled/dynamic-buttons-simple.html");

        getDriver().findElement(By.id("button00")).click();
        getDriver().findElement(By.id("button01")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button02")));
        getDriver().findElement(By.id("button02")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button03")));
        getDriver().findElement(By.id("button03")).click();

        Assert.assertEquals(getDriver().findElement(By.id("buttonmessage")).getText(), "All Buttons Clicked", "Not All Buttons Clicked");
    }

    @Test
    public void testXHTTPMessages() {
        getDriver().get("https://testpages.eviltester.com/styled/sync/xhttp-messages.html");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.id("messagesstatus")), "Message Count: 0 : 0"));

        Assert.assertEquals(getDriver().findElement(By.id("messagesstatus")).getText(), "Message Count: 0 : 0", "message count is not zero");
    }

    @Test
    public void testDisabledDynamicButtons() {
        getDriver().get(BUTTONS_URL);

        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button00"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button01"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button02"))).click();
        getWait15().until(ExpectedConditions.elementToBeClickable(By.id("button03"))).click();

        Boolean found = getWait15().until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonmessage"),
                "All Buttons Clicked"));
        Assert.assertTrue(found, "Text \"All Buttons Clicked\" not found");
    }

    @DataProvider(name = "alertDataProvider")
    public Object[][] alertDataProvider() {
        return new Object[][]{
                {"alertexamples", "alertexplanation", "You triggered and handled the alert dialog", null, true},
                {"confirmexample", "confirmexplanation", "You clicked OK, confirm returned true.", null, true},
                {"confirmexample", "confirmexplanation", "You clicked Cancel, confirm returned false.", null, false},
                {"promptexample", "promptexplanation", "You clicked OK. 'prompt' returned some random input", "some random input", true},
                {"promptexample", "promptexplanation", "You clicked Cancel. 'prompt' returned null", "some random input", false}
        };
    }

    @Test(dataProvider = "alertDataProvider")
    public void testAlerts(String buttonId, String messageId, String expected, String keys, boolean accept) {
        getDriver().get(ALERTS_URL);

        getDriver().findElement(By.id(buttonId)).click();

        Alert alert = getWait15().until(ExpectedConditions.alertIsPresent());

        if (keys != null) {
            alert.sendKeys(keys);
        }

        if (accept) {
            alert.accept();
        } else {
            alert.dismiss();
        }

        Assert.assertEquals(
                getDriver().findElement(By.id(messageId)).getText(),
                expected);
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

    @DataProvider(name = "fakeAlertDataProvider")
    public Object[][] fakeAlertDataProvider() {
        return new Object[][]{
                {By.id("fakealert"), MODAL_DIALOG_OK_BUTTON},
                {OPEN_MODAL_DIALOG_BUTTON, MODAL_DIALOG_OK_BUTTON}
        };
    }

    @Test(dataProvider = "fakeAlertDataProvider")
    public void testFakeAlertCloseByClickOnOkButton(By triggerAlertButton, By okAlertButton) {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(triggerAlertButton).click();

        WebElement message = getDriver().findElement(By.id("dialog-text"));

        getDriver().findElement(okAlertButton).click();

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @DataProvider(name = "fakeAlertKeysProvider")
    public Object[][] fakeAlertKeysProvider() {
        return new Object[][]{{Keys.ENTER}, {Keys.ESCAPE}};
    }

    @Test(dataProvider = "fakeAlertKeysProvider")
    public void testFakeModalAlertCloseBySendKeyToOkAlertButton(Keys key) {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        getDriver().findElement(MODAL_DIALOG_OK_BUTTON).sendKeys(key);

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByJsDispatchEventToDocument() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        ((JavascriptExecutor) getDriver()).executeScript(
                "document.dispatchEvent(new KeyboardEvent('keydown', {'key': 'Esc'}));");

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByJsClickOnBackground() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();",
                getDriver().findElement(By.cssSelector(".faded-background.active")));

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
    }

    @Test
    public void testFakeModalAlertCloseByMoveToLocationAndClickOnBackground() {
        getDriver().get(FAKE_ALERTS_URL);

        getDriver().findElement(OPEN_MODAL_DIALOG_BUTTON).click();

        WebElement message = getDriver().findElement(MODAL_DIALOG_TEXT);

        new Actions(getDriver())
                .moveToLocation(0, 0)
                .click()
                .perform();

        Assert.assertFalse(message.isDisplayed(), ALERT_DISPLAYED);
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

    @Test
    public void testBasicAuthProtection() {
        ((ChromeDriver) getDriver()).executeCdpCommand(
                "Network.enable", Map.of());
        ((ChromeDriver) getDriver()).executeCdpCommand(
                "Network.setExtraHTTPHeaders",
                Map.of("headers", Map.of("Authorization", "Basic YXV0aG9yaXplZDpwYXNzd29yZDAwMQ==")));

        getDriver().get("https://testpages.eviltester.com/styled/auth/basic-auth-results.html");

        Assert.assertEquals(
                getDriver().findElement(By.id("status")).getText(),
                "Authenticated");
    }

    @Test
    public void testBasicAuthorization() {
        getDriver().get("https://authorized:password001@testpages.eviltester.com/styled/auth/basic-auth-results.html");
        Assert.assertEquals(
                getDriver().findElement(By.id("status")).getText(),
                "Authenticated");
    }
}

