package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "Organization Folder";

    private void createOrganizationFolder(String name){
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    @Test
    public void testCreateOrganizationFolder() {
        createOrganizationFolder("Organization Folder");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Organization Folder");
    }

    @Test
    public void testOrganizationFolderCreationWithDefaultIcon() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ORGANIZATION_FOLDER_NAME);
        getDriver().findElement(By.cssSelector("[class$='OrganizationFolder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        new Select(getDriver().findElement(By.xpath("(//select[contains(@class, 'dropdownList')])[2]")))
                .selectByVisibleText("Default Icon");
        getDriver().findElement(By.name("Submit")).click();

        String organizationFolderIcon = getDriver().findElement(By.cssSelector("h1 > svg")).getAttribute("title");
        Assert.assertEquals(organizationFolderIcon, "Folder");
    }
}
