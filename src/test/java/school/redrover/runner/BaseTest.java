package school.redrover.runner;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import school.redrover.runner.order.OrderForTests;
import school.redrover.runner.order.OrderUtils;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private WebDriverWait wait2;

    private WebDriverWait wait5;

    private WebDriverWait wait10;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    private void startDriver() {
        startDriver("chrome");
    }

    private void startDriver(String browser) {
        ProjectUtils.log(Level.DEBUG, "Browser open");
        driver.set(ProjectUtils.createDriver(browser));
    }

    private void clearData() {
        ProjectUtils.log(Level.DEBUG, "Clear data");
        JenkinsUtils.clearData();
    }

    private void loginWeb() {
        ProjectUtils.log(Level.DEBUG, "Login");
        JenkinsUtils.login(getDriver());
    }

    private void getWeb() {
        ProjectUtils.log(Level.DEBUG, "Get web page");
        ProjectUtils.get(getDriver());
    }

    private void stopDriver() {
        try {
            JenkinsUtils.logout(getDriver());
        } catch (Exception ignore) {

        }

        closeDriver();
    }

    private void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();

            wait2 = null;
            wait5 = null;
            wait10 = null;

            ProjectUtils.log(Level.DEBUG, "Browser closed");
        }
    }

    private void acceptAlert() {
        ProjectUtils.acceptAlert(getDriver());
    }

    @BeforeClass
    protected void beforeClass() {
        methodsOrder = OrderUtils.createMethodsOrder(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .toList(),
                Method::getName,
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    @Parameters("browser")
    protected void beforeMethod(@Optional("chrome") String browser, Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method) || methodsOrder.isGroupFinished(method)) {
                clearData();
                startDriver(browser);
                getWeb();
                loginWeb();
            } else {
                getWeb();
                acceptAlert();
            }
        } catch (Exception e) {
            closeDriver();
            throw new RuntimeException(e);
        } finally {
            methodsOrder.markAsInvoked(method);
        }
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectUtils.isServerRun()) {
            ProjectUtils.takeScreenshot(getDriver(), testResult.getInstanceName(), testResult.getName());
        }

        if (!testResult.isSuccess()) {
            Allure.addAttachment(
                    "screenshot.png",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)),
                    "png");
        }

        boolean shouldStop = ProjectUtils.isServerRun() || testResult.isSuccess() || ProjectUtils.closeBrowserIfError();
        if (methodsOrder.isGroupFinished(method) && shouldStop) {
            stopDriver();
        }

        ProjectUtils.logf(
                "Execution time is %.3f sec",
                (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }

    protected WebDriver getDriver() {
        if (driver.get() == null) {
            startDriver();
        }
        return driver.get();
    }

    protected WebDriverWait getWait2() {
        if (wait2 == null) {
            wait2 = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }

        return wait2;
    }

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }

        return wait5;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }

        return wait10;
    }
}
