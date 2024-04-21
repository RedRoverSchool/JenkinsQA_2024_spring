package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class MultibranchPipeline2Test extends BaseTest {

    @Test
    public void testEnableMultibranchPipeline() {

        getDriver().findElement(By.xpath("//span[.='Create a job']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("TextName");
        getDriver().findElement(By.xpath("//span[.='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//label[@data-title='Disabled']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//span[.='Configure the project']")).click();
        getDriver().findElement(By.xpath("//label[@data-title='Disabled']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//span[.='Configure the project']")).click();

        String statusToggle = getDriver().findElement(By.id("enable-disable-project")).getDomProperty("checked");

        Assert.assertEquals(statusToggle, "true");
    }

    @Test
    public void testDisabledMultibranchPipeline() {

        getDriver().findElement(By.xpath("//span[.='Create a job']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("TextName1");
        getDriver().findElement(By.xpath("//span[.='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//label[@data-title='Disabled']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//span[.='Configure the project']")).click();

        String statusToggle = getDriver().findElement(By.id("enable-disable-project")).getDomProperty("checked");

        Assert.assertEquals(statusToggle, "false");
    }
}