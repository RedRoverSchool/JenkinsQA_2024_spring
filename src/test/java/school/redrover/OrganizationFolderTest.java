package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

import static school.redrover.runner.TestUtils.createNewItemAndReturnToDashboard;


public class OrganizationFolderTest extends BaseTest {
    private static final String ORGANIZATION_FOLDER_NAME = "Organization Folder";

    private void createOrganizationFolder(String name) {
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
    public void testCreateWithDefaultIcon() {
        String organizationFolderIcon = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getOrganizationFolderIcon();

        Assert.assertEquals(organizationFolderIcon, "Folder");
    }

    @Test
    public void testPipelineSyntaxDocumentationAccess() {
        createNewItemAndReturnToDashboard(this, ORGANIZATION_FOLDER_NAME, TestUtils.Item.ORGANIZATION_FOLDER);

        getDriver().findElement(By.xpath("//span[contains(text(), '" + ORGANIZATION_FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'pipeline-syntax')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Online Documentation')]/..")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/pipeline/"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='pipeline-syntax']")).getText(), "Pipeline Syntax");
    }

    @Test
    public void testPipelineSyntaxExamplesAccess() {
        createNewItemAndReturnToDashboard(this, ORGANIZATION_FOLDER_NAME, TestUtils.Item.ORGANIZATION_FOLDER);

        getDriver().findElement(By.xpath("//span[contains(text(), '" + ORGANIZATION_FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'pipeline-syntax')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'examples')]")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/examples/"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(@id,'examples')]")).getText(), "Pipeline Examples");
    }

    @Test(dependsOnMethods = "testCreateWithDefaultIcon")
    public void testCatchErrorStepTooltipsViaDashboardDropdown() {
        final List<String> expectedTooltipList = List.of(
                "Help for feature: catchError",
                "Help for feature: Message",
                "Help for feature: Build result on error",
                "Help for feature: Stage result on error",
                "Help for feature: Catch Pipeline interruptions");

        List<String> actualTooltipList = new HomePage(getDriver())
                .openItemDropdownWithSelenium(ORGANIZATION_FOLDER_NAME)
                .openItemPipelineSyntaxFromDropdown()
                .selectCatchError()
                .getCatchErrorTooltipList();

        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test
    public void testSidebarMenuVisibility() {
        createOrganizationFolder(ORGANIZATION_FOLDER_NAME);

        OrganizationFolderProjectPage organizationFolderProjectPage = new OrganizationFolderProjectPage(getDriver());
        Assert.assertTrue(organizationFolderProjectPage
                .clickConfigure()
                .isSidebarVisible());
    }
}
