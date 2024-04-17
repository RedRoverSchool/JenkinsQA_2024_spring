package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder2Test extends BaseTest{

    @Test
    public void testCreateOrganizationFolder() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization Folder");
        getDriver().findElement(By.xpath("//label[.='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[.='Dashboard']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[.='Organization Folder']")).isDisplayed());
    }
}
