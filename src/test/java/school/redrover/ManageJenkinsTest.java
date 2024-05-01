package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testRedirectionToSecurityPage() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='configureSecurity']")).click();

        String pageTitle = getDriver().getTitle().split(" ")[0];
        Assert.assertEquals(pageTitle, "Security");
    }
}
