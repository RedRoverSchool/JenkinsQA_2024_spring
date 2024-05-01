package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline4Test extends BaseTest {
    @Test
    public void testCreateMultibranchPipeline(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("First Multibranch pipeline");
        getDriver().findElement(By.xpath("//*[contains(@class,'MultiBranch')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testRenamingMultibranchPipeline() {
        getDriver().findElement(By.xpath
                ("//*[@href='job/First%20Multibranch%20pipeline/']/span")).click();
        getDriver().findElement(By.xpath
                ("//*[contains(@href,'confirm-rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//*[@name='newName']"))
                .sendKeys("New project");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "New project");
    }
}
