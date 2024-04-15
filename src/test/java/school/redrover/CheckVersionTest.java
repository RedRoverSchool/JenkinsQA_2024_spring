package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckVersionTest extends BaseTest {

    @Test
    public void testCheckVersion() {
        getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.xpath("//*[@id='tippy-1']/div/div/div/a[1]")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//*[@id='main-panel']/div[2]/div[1]/p")).getText(), "Version 2.440.2");
    }
}
