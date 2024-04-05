package school.redrover;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import school.redrover.runner.BaseTest;

import java.lang.reflect.Method;
import java.time.Duration;

public class AqaBaseTest extends BaseTest {
    private WebDriverWait wait15;

    @BeforeMethod
    @Override
    protected void beforeMethod(Method method) {
        super.beforeMethod(method);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    protected WebDriverWait getWait15() {
        if (wait15 == null) {
            wait15 = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        }
        return wait15;
    }

    @AfterMethod
    @Override
    protected void afterMethod(Method method, ITestResult testResult) {
        super.afterMethod(method, testResult);
        if (wait15 != null) {
            wait15 = null;
        }
    }
}
