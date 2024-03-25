package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupEviltesterTest extends AqaGroupBaseTest {

    private static final String BUTTONS_URL = "https://testpages.eviltester.com/styled/dynamic-buttons-disabled.html";
    private static final String ALERTS_URL = "https://testpages.eviltester.com/styled/alerts/alert-test.html";

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
        String link = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            driver.findElement(By.id("alertexamples")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();

            Assert.assertEquals(
                    driver.findElement(By.id("alertexplanation")).getText(),
                    "You triggered and handled the alert dialog");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testAcceptConfirmAlert() {
        String link = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            driver.findElement(By.id("confirmexample")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();

            Assert.assertEquals(
                    driver.findElement(By.id("confirmexplanation")).getText(),
                    "You clicked OK, confirm returned true.");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDismissConfirmAlert() {
        String link = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            driver.findElement(By.id("confirmexample")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();

            Assert.assertEquals(
                    driver.findElement(By.id("confirmexplanation")).getText(),
                    "You clicked Cancel, confirm returned false.");
        } finally {
            driver.quit();
        }
    }


    @Test
    public void testAcceptPromptAlert() {
        String link = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            driver.findElement(By.id("promptexample")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String myKeys = "some random input";
            alert.sendKeys(myKeys);
            alert.accept();

            Assert.assertEquals(
                    driver.findElement(By.id("promptexplanation")).getText(),
                    String.format("You clicked OK. 'prompt' returned %s", myKeys));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDismissPromptAlert() {
        String link = "https://testpages.eviltester.com/styled/alerts/alert-test.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);

            driver.findElement(By.id("promptexample")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();

            Assert.assertEquals(
                    driver.findElement(By.id("promptexplanation")).getText(),
                    "You clicked Cancel. 'prompt' returned null");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testExpandingDivWithClickableLink() {
        String link = "https://testpages.eviltester.com/styled/expandingdiv.html";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(link);
            new Actions(driver)
                    .moveToElement(driver.findElement(By.className("expand")))
                    .pause(500)
                    .moveToElement(driver.findElement(By.cssSelector(".expand p a")))
                    .click()
                    .pause(500)
                    .perform();

            Assert.assertTrue(driver.getCurrentUrl().contains("expandeddiv"), "Unexpected URL.");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void fakeAlertTest() {
        driver.get("https://testpages.eviltester.com/styled/alerts/fake-alert-test.html");

        driver.findElement(By.id("fakealert")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.id("dialog-ok")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake alert box is active");
    }

    @Test
    public void fakeModalAlertCloseOkTest() {
        driver.get("https://testpages.eviltester.com/styled/alerts/fake-alert-test.html");

        driver.findElement(By.id("modaldialog")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.id("dialog-ok")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake modal alert box is active");
    }

    @Test
    public void fakeModalAlertCloseBackgroundTest() {
        driver.get("https://testpages.eviltester.com/styled/alerts/fake-alert-test.html");

        driver.findElement(By.id("modaldialog")).click();
        WebElement message = driver.findElement(By.id("dialog-text"));
        driver.findElement(By.cssSelector(".faded-background.active")).click();

        Assert.assertFalse(
                message.isDisplayed(),
                "fake modal alert box is active");
    }

}

