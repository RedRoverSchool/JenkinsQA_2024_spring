package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder6Test extends BaseTest {

    @Test
    public void testCreateOrganization6Folder() {
        final String orgFolderName = "organization folder";
        getDriver().findElement(By.xpath("//a[@class='task-link task-link-no-confirm '][@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(orgFolderName);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.className("jenkins-breadcrumbs__list-item")).click();

        String actualCreatedFolder = getDriver().findElement(By.xpath(
                "//tr[@id='job_" + orgFolderName +"']//a[@class='jenkins-table__link model-link inside']")).getText();
        Assert.assertEquals(actualCreatedFolder, orgFolderName);
    }
}

