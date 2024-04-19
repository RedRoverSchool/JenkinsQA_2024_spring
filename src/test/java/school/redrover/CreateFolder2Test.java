package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFolder2Test extends BaseTest {

    @Ignore
    @Test
    public void testCreateNewFolder() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Folder");
        getDriver().findElement(By.xpath("//label[.='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[.='Dashboard']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[.='New Folder']")).isDisplayed());
    }
}