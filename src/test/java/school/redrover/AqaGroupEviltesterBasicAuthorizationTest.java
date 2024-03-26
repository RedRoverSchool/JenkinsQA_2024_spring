package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AqaGroupEviltesterBasicAuthorizationTest extends AqaGroupBaseTest{
    @Test
    public void testBasicAuthorization(){
        driver.get("https://authorized:password001@testpages.eviltester.com/styled/auth/basic-auth-results.html");
        Assert.assertEquals(driver.findElement(By.id("status")).getText(),"Authenticated");

    }

}