package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleCreateTest extends BaseTest {

    @Test
    public void testFreestyleCreate() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("project1");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(), "project1");
    }
}
