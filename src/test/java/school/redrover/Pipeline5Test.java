package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline5Test extends BaseTest {
    @Test
    public void testCreateMultibranchPipeline(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("First Multibranch Pipeline");
        getDriver().findElement(By.xpath("//span[(.='Multibranch Pipeline')]")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        String title = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(title,"First Multibranch Pipeline");
    }
}
