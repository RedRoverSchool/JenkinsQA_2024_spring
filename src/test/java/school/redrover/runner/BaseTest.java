package school.redrover.runner;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import school.redrover.runner.order.OrderForTests;
import school.redrover.runner.order.OrderUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    private WebDriverWait wait2;

    private WebDriverWait wait5;

    private WebDriverWait wait10;

    private WebDriverWait wait60;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    private void startDriver() {
        ProjectUtils.log(Level.DEBUG, "Browser open");

        driver = ProjectUtils.createDriver();
    }

    private void clearData() {
        ProjectUtils.log(Level.DEBUG, "Clear data");
        JenkinsUtils.clearData();
    }

    private void loginWeb() {
        ProjectUtils.log(Level.DEBUG, "Login");
        JenkinsUtils.login(driver);
    }

    private void getWeb() {
        ProjectUtils.log(Level.DEBUG, "Get web page");
        ProjectUtils.get(driver);
    }

    private void stopDriver() {
        try {
            JenkinsUtils.logout(driver);
        } catch (Exception ignore) {}

        closeDriver();
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();

            driver = null;

            wait2 = null;
            wait5 = null;
            wait10 = null;
            wait60 = null;

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
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method) || methodsOrder.isGroupFinished(method)) {
                clearData();
                startDriver();
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

        if (methodsOrder.isGroupFinished(method) && !(!ProjectUtils.isServerRun() && !testResult.isSuccess() && !ProjectUtils.closeBrowserIfError())) {
            stopDriver();
        }

        ProjectUtils.logf("Execution time is %.3f sec", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }

    protected WebDriver getDriver() {
        return driver;
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

    protected WebDriverWait getWait60() {
        if (wait60 == null) {
            wait60 = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        }

        return wait60;
    }
}
