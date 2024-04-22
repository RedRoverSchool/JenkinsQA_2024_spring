package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OKButtonUnavailableTest extends BaseTest {

    @Test
    public void testOKButtonIsUnavailable() {

        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).click();
        getDriver().findElement(By.id("name")).sendKeys("");
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());

    }
}
