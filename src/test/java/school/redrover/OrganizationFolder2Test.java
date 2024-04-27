package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

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

    @Test(dependsOnMethods = "testCreateOrganizationFolder")
    public void testOrganizationFolderAddDescription(){

        getDriver().findElement(By.xpath("//span[.='Organization Folder']")).click();
        getDriver().findElement(By.xpath("//*[@href='/job/Organization%20Folder/configure']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys("Some description of the folder");
        getDriver().findElement(By.xpath("//div/*[@name='Submit']")).click();

        String textOfDescription = getDriver().findElement(By.xpath("//div/*[@id='view-message']")).getText();

        Assert.assertEquals(textOfDescription,"Some description of the folder");
    }

    @Test(dependsOnMethods = "testOrganizationFolderAddDescription")
    public void testDeleteOrganizationFolder() {

        getDriver().findElement(By.xpath("//span[.='Organization Folder']")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Organization Folder']")).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver().findElements(
                By.xpath("//span[.='Organization Folder']"));

        Assert.assertTrue(jobList.isEmpty());
    }
}
