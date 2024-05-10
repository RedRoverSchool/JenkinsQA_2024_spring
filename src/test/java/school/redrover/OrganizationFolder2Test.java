package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class OrganizationFolder2Test extends BaseTest{

    @Test
    public void testCreateOrganizationFolder() {
        final String organizationFolderName = "New Organization Folder";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(organizationFolderName));
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolder")
    public void testRenameOrganizationFolder() {
        getDriver().findElement(By.xpath("//span[.='New Organization Folder']")).click();
        getDriver().findElement(By.xpath("//a[contains(.,'Rename')]")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(
                "Organization Folder");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[.='Dashboard']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath(
                "//span[.='Organization Folder']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testRenameOrganizationFolder")
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
