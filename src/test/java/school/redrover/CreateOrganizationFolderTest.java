package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateOrganizationFolderTest extends BaseTest {
    public static final String name = "Test Organization Folder";

    @Test
    public void testCreateOrganizationFolder() {
        createOrganizationFolder(name);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), name);
    }

    private void createOrganizationFolder(String name){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
}
