package school.redrover;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class AqaGroupBaseTest {
    protected WebDriver driver;
    private WebDriverWait wait5;
    private WebDriverWait wait15;
    private WebDriverWait wait25;
    private WebDriverWait wait60;

    @BeforeMethod
    protected void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
//        chromeOptions.addArguments("--headless");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebElement scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        }
        return wait5;
    }

    protected WebDriverWait getWait15() {
        if (wait15 == null) {
            wait15 = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        return wait15;
    }

    protected WebDriverWait getWait25() {
        if (wait25 == null) {
            wait25 = new WebDriverWait(driver, Duration.ofSeconds(25));
        }
        return wait25;
    }

    protected WebDriverWait getWait60() {
        if (wait60 == null) {
            wait60 = new WebDriverWait(driver, Duration.ofSeconds(60));
        }
        return wait60;
    }

    @AfterMethod
    protected void tearDown() {
        if (driver != null)
            driver.quit();
        wait5 = null;
        wait15 = null;
        wait25 = null;
        wait60 = null;
    }
}
