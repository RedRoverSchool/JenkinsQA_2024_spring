package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    final String ORGANIZATION_FOLDER_NAME = "NewOrgFolder";

    private void createOrganizationFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text() ='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Ignore
    @Test
    public void testCreateOrganizationFolder(){
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ORGANIZATION_FOLDER_NAME);
        getDriver().findElement(By.xpath("//label/span[text() ='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), ORGANIZATION_FOLDER_NAME);
    }

    @Test
    public void testFindOrganizationFolderOnDashboard(){
        createOrganizationFolder(ORGANIZATION_FOLDER_NAME);
        openDashboard();
        String actualName = getDriver().findElement(By.linkText(ORGANIZATION_FOLDER_NAME)).getText();

        Assert.assertEquals(actualName, ORGANIZATION_FOLDER_NAME);
    }
}