package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AqaGroupEviltesterBasicAuthorizationTest extends AqaGroupBaseTest {

    @Test
    public void testBasicAuthorization() {
        getDriver().get("https://authorized:password001@testpages.eviltester.com/styled/auth/basic-auth-results.html");
        Assert.assertEquals(
                getDriver().findElement(By.id("status")).getText(),
                "Authenticated");
    }
}