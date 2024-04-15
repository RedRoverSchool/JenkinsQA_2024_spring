package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class LogoutTest extends BaseTest {
    @Test
    public void testLogout() {
        getDriver().findElement(By.xpath("//*[@href='/logout']")).click();
        String actual = getDriver().findElement(
                By.xpath("//*[text()='Sign in to Jenkins']")).getText();

        Assert.assertEquals(actual, "Sign in to Jenkins");
    }
}
