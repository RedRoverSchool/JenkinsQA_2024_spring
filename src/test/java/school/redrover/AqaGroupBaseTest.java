package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public abstract class AqaGroupBaseTest extends BaseTest {

    private WebDriverWait wait5;
    private WebDriverWait wait15;
    private WebDriverWait wait25;
    private WebDriverWait wait60;

    @BeforeMethod
    protected void beforeMethod() {
        getDriver().manage().window().setSize(new Dimension(1920, 1080));
    }

    protected WebElement scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    protected WebDriverWait getWait(int seconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
    }

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }
        return wait5;
    }

    protected WebDriverWait getWait15() {
        if (wait15 == null) {
            wait15 = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        }
        return wait15;
    }

    protected WebDriverWait getWait25() {
        if (wait25 == null) {
            wait25 = new WebDriverWait(getDriver(), Duration.ofSeconds(25));
        }
        return wait25;
    }

    protected WebDriverWait getWait60() {
        if (wait60 == null) {
            wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        }
        return wait60;
    }

    @AfterMethod
    protected void afterMethod() {
        wait5 = null;
        wait15 = null;
        wait25 = null;
        wait60 = null;
    }
}
