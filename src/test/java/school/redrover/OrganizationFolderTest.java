package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.createNewItemAndReturnToDashboard;

public class OrganizationFolderTest extends BaseTest {
    private static final String ORGANIZATION_FOLDER_NAME = "Organization Folder";

    private void createOrganizationFolder(String name){
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    @Ignore
    @Test
    public void testCreateOrganizationFolder() {
        createOrganizationFolder("Organization Folder");
        WebElement disableOrganizationFolderButton = getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']"));

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Organization Folder");
        Assert.assertEquals(disableOrganizationFolderButton.getAttribute("name"), "Submit", "Button name attribute does not match");
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
    @Test
    public void testPipelineSyntaxDocumentationAccess() {
        createNewItemAndReturnToDashboard(this, ORGANIZATION_FOLDER_NAME, TestUtils.Item.ORGANIZATION_FOLDER);

        getDriver().findElement(By.xpath("//span[contains(text(), '"+ ORGANIZATION_FOLDER_NAME +"')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'pipeline-syntax')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Online Documentation')]/..")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/pipeline/"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='pipeline-syntax']")).getText(), "Pipeline Syntax");
    }
    @Test
    public void testPipelineSyntaxExamplesAccess() {
        createNewItemAndReturnToDashboard(this, ORGANIZATION_FOLDER_NAME, TestUtils.Item.ORGANIZATION_FOLDER);

        getDriver().findElement(By.xpath("//span[contains(text(), '"+ ORGANIZATION_FOLDER_NAME +"')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'pipeline-syntax')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'examples')]")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/examples/"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(@id,'examples')]")).getText(), "Pipeline Examples");
    }
}
