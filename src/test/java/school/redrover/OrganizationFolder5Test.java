package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder5Test extends BaseTest {
    private final String ORG_FOLDER_NAME = "EmptyFolder";

    private void createOrganizationFolder(String orgFolderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(orgFolderName);
        getDriver().findElement(By.xpath("//label[normalize-space(.)='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testViewEmptyOrganizationFolderEvents() {
        createOrganizationFolder(ORG_FOLDER_NAME);

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.linkText(ORG_FOLDER_NAME)).click();
        getDriver().findElement(By.linkText("Organization Folder Events")).click();

        //Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='out']/div")).getText(), "No events as of.+waiting for events\\.{3}", "Messages not equals!");
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='out']/div")).getText()
                .matches("No events as of.+waiting for events\\.{3}"), "Messages not equals!");
    }
}