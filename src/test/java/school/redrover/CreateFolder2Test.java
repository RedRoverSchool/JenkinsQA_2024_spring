package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFolder2Test extends BaseTest {

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Folder");
        getDriver().findElement(By.xpath("//label[.='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.name("Submit"))).click();

        Assert.assertTrue(getDriver().findElement(By.tagName("h1")).isDisplayed());
    }
}