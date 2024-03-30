package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public abstract class BaseTest {

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        driver = new ChromeDriver();
    }

    @AfterMethod
    protected void afterMethod(Method method) {
        driver.quit();
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
