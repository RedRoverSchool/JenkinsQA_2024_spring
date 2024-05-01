package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline06Test extends BaseTest {
    @Test
    public void testCreateMultibranchPipeline(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch pipeline");
        getDriver().findElement(By.xpath("//*[contains(@class,'MultiBranch')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testEnableMultibranchPipeline(){
        getDriver().findElement(By.xpath
                ("//*[@href='job/New%20Multibranch%20pipeline/']/span")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertNotNull(getDriver().findElements(By.xpath(
                "//*[contains(@src, 'pipelinemultibranchproject.svg')]")));
    }
}
