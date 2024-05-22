package school.redrover;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private static final String RENAMED_MULTI_PIPELINE = "NewMultibranchPipelineName";
    private static final String FOLDER_NAME = "Folder";

    @Test
    public void testCreateProjectViaSidebarMenu() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, MULTI_PIPELINE_NAME, "Item is not found");
    }

    @Test
    public void testCreateProjectWithEmptyName() {
        String errorName = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfigurationAndClickOk()
                .getErrorRequiresName();

        Assert.assertTrue(errorName.contains("This field cannot be empty, please enter a valid name"));
    }

    @Test
    public void testCreateMultibranchPipelineFromExistingMultibranchPipeline() {
        final String FIRST_ITEM_NAME = "My first Multibranch Pipeline";
        final String SECOND_ITEM_NAME = "My second Multibranch Pipeline";

        TestUtils.createMultibranchProject(this, FIRST_ITEM_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(SECOND_ITEM_NAME)
                .setItemNameInCopyForm(FIRST_ITEM_NAME)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, SECOND_ITEM_NAME,
                "Copy of " + FIRST_ITEM_NAME + "not created!");
    }

    @Test
    public void testCreateMultibranchPipelineWithEmptyName() {
        final String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                .click();
        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));

        Assert.assertFalse(okButton.isEnabled());
        Assert.assertTrue(actualErrorMessage.isDisplayed());
        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testRenameMultibranchPipelineViaSideBar() {
        List<String> itemList = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .clickSidebarRenameButton()
                .clearNewNameInput()
                .setItemName(RENAMED_MULTI_PIPELINE)
                .clickRename(new MultibranchPipelineProjectPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, RENAMED_MULTI_PIPELINE, "Item is not renamed successfully");
    }

    @Test
    public void testRenameProjectWithNameSameAsCurrent() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String errorMessage = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickSidebarRenameButton()
                .clearNewNameInput()
                .setItemName(MULTI_PIPELINE_NAME)
                .clickRename(new MultibranchPipelineErrorPage(getDriver()))
                .getErrorText();

        Assert.assertEquals(errorMessage, "The new name is the same as the current name.");

    }

    @Test
    public void testChangeFromDisabledToEnabledOnStatusPage() {
        MultibranchPipelineProjectPage multibranchPipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .clickDisableEnableMultibranchPipeline();

        Assert.assertTrue(multibranchPipelineProjectPage.isMultibranchPipelineDisabledTextNotDisplayed(), "Disabled message is displayed!!!");
    }

    @Test
    public void testVerifyStatusToSwitchingEnableMultibranchPipeline() {
        String enableStatus = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .clickDisableEnableMultibranchPipeline()
                .getDisableMultibranchPipelineButtonText();

        Assert.assertEquals(enableStatus, "Disable Multibranch Pipeline");
    }

    @Test
    public void testDisabledTooltip() {
        final String tooltipText = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        MultibranchPipelineConfigPage multibranchPipelineConfigPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .hoverOverToggle();

        Assert.assertTrue(multibranchPipelineConfigPage.isTooltipDisplayed());
        Assert.assertEquals(multibranchPipelineConfigPage.getTooltipText(), tooltipText);
    }

    @Test
    public void testRenameMultibranchPipelineViaMainPageDropdownMenu() {

        List<String> itemList = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickLogo()
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickRenameFromDropdownMP()
                .clearNewNameInput()
                .setItemName(RENAMED_MULTI_PIPELINE)
                .clickRename(new MultibranchPipelineProjectPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, RENAMED_MULTI_PIPELINE, "Item is not renamed successfully");
    }

    @Test
    public void testRenamedMultibranchPipelineSeenInBreadcrumbs() {
        String multiPipelineBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .sendItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickSidebarRenameButton()
                .clearNewNameInput()
                .setItemName(RENAMED_MULTI_PIPELINE)
                .clickRename(new MultibranchPipelineProjectPage(getDriver()))
                .getBreadcrumbsProjectName();

        Assert.assertEquals(multiPipelineBreadcrumbName, RENAMED_MULTI_PIPELINE,
                "Actual multibranch breadcrumb name is not " + RENAMED_MULTI_PIPELINE);
    }

    @Test
    public void testMultibranchSidebarTasksUponCreatingViaFolder() {
        final List<String> sidebarTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events", "Delete Multibranch Pipeline", "People", "Build History", "Move",
                "Rename", "Pipeline Syntax", "Credentials");

        MultibranchPipelineProjectPage multibranchPipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName("NestedTestsFolder")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickNewItemInsideFolder()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton();

        Assert.assertEquals(multibranchPipelineProjectPage.getSidebarTasksSize(), 11);
        Assert.assertEquals(multibranchPipelineProjectPage.getSidebarTasksListHavingExistingFolder(), sidebarTasks);
    }

    @Test
    public void testVerifyProjectSidebarMenu() {
        final List<String> pipelineSidebarList =
                List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
                        "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");

        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//span[text() = '" + MULTI_PIPELINE_NAME + "']")).click();
        List<String> pipelineSideMenu = TestUtils.getTexts(getDriver()
                .findElements(By.xpath("//div[@class = 'task ']")));

        Assert.assertEquals(pipelineSideMenu.size(), 10);
        Assert.assertEquals(pipelineSideMenu, pipelineSidebarList);
    }

    @Test
    public void testMoveInFolderViaSidebarMenu() {
        TestUtils.createNewItem(this, FOLDER_NAME, TestUtils.Item.FOLDER);
        TestUtils.createNewItem(this, MULTI_PIPELINE_NAME, TestUtils.Item.MULTI_BRANCH_PIPELINE);

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='move']")).click();
        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        WebElement searchInput = getDriver().findElement(By.cssSelector("input[role='searchbox']"));
        searchInput.sendKeys(MULTI_PIPELINE_NAME);

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.className("yui-ac-bd")));
        String actualFolderNameInSearch = getDriver().findElements(By.className("yui-ac-bd"))
                .stream()
                .map(WebElement::getText)
                .toList().toString()
                .replace("[", "")
                .replace("]", "")
                .replace(MULTI_PIPELINE_NAME, "")
                .trim();

        Assert.assertEquals(actualFolderNameInSearch, FOLDER_NAME, MULTI_PIPELINE_NAME + "находится в другой папке");
    }

    @Ignore
    @Test
    public void testDeleteViaDashboardDropdown() {
        String actualPageHeading = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickLogo()
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .getHeadingValue();

        Assert.assertEquals(actualPageHeading, "Welcome to Jenkins!");
    }

    @Test
    public void testEnableMultibranchPipeline() {
        MultibranchPipelineConfigPage page = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure();

        Assert.assertEquals(page.getStatusToggle(), "true");
    }

    @Test
    public void testDisabledMultibranchPipeline() {
        MultibranchPipelineConfigPage page = new HomePage(getDriver()).clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure();

        Assert.assertEquals(page.getStatusToggle(), "false");
    }

    @Test
    public void testRenamingMultibranchPipeline() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        getDriver().findElement(By.xpath
                ("//*[@href='job/" + MULTI_PIPELINE_NAME + "/']/span")).click();
        getDriver().findElement(By.xpath
                ("//*[contains(@href,'confirm-rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//*[@name='newName']"))
                .sendKeys("New project");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "New project");
    }

    @Test
    public void testEnableMultibranchPipeline2() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        getDriver().findElement(By.xpath
                ("//*[@href='job/" + MULTI_PIPELINE_NAME + "/']/span")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertNotNull(getDriver().findElements(By.xpath(
                "//*[contains(@src, 'pipelinemultibranchproject.svg')]")));
    }

    @Test
    public void testNewMultibranchPipelineIsEmpty() {
        final String multibranchPipelineName = "FidelityNewPipeline";
        final String thisFolderIsEmptyMessage = "This folder is empty";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(multibranchPipelineName);
        getDriver().findElement(By.xpath("//label/span[text() ='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.goToMainPage(getDriver());

        getDriver().findElement(By.linkText(multibranchPipelineName)).click();

        final String actualMultibranchPipelineName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();

        Assert.assertEquals(actualMultibranchPipelineName, multibranchPipelineName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledMessageColorOnStatusPage")
    public void testChangeFromDisableOnStatusPage() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        WebElement configureLink = getDriver().findElement(By.cssSelector(".task-link-wrapper [href$='configure']"));
        configureLink.click();
        if (getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title"))
                .isDisplayed()) {
            getDriver().findElement(By.cssSelector("[data-title*='Disabled']")).click();
        }
        getDriver().findElement(By.cssSelector("[name*='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[contains(., 'Enable')]")).click();
        List<WebElement> disabledMultiPipelineMessage = getDriver().findElements(
                By.xpath("//form[contains(., 'This Multibranch Pipeline is currently disabled')]"));

        Assert.assertEquals(disabledMultiPipelineMessage.size(), 0, "Disabled message is displayed!!!");
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    public void testVerifyMpDisabledOnStatusPage() {
        String disabledMessage = new HomePage(getDriver())
                .clickMPName(MULTI_PIPELINE_NAME)
                .clickDisableEnableMultibranchPipeline()
                .getDisableMultibranchPipelineText();

        Assert.assertEquals(disabledMessage, "This Multibranch Pipeline is currently disabled");
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledOnStatusPage")
    public void testVerifyMpDisabledMessageColorOnStatusPage() {
        String disabledMessageColor = new HomePage(getDriver())
                .clickMPName(MULTI_PIPELINE_NAME)
                .getDisableMultibranchPipelineTextColor();

        Assert.assertEquals(disabledMessageColor, "rgba(254, 130, 10, 1)");
    }

    @Ignore
    @Test(dependsOnMethods = "testRenameMultibranchPipelineViaSideBar")
    public void testDeleteMpViaBreadcrumbs() {
        boolean isMpDeleted = new HomePage(getDriver())
                .clickMPName(RENAMED_MULTI_PIPELINE)
                .clickMPDropdownArrow()
                .clickDeleteMultibranchPipelineInBreadcrumbs(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(RENAMED_MULTI_PIPELINE);

        Assert.assertTrue(isMpDeleted, RENAMED_MULTI_PIPELINE + " was not deleted");
    }

    @Test(dependsOnMethods = "testDisabledMultibranchPipeline")
    public void testMultibranchPipelineEnable() {
        String buttonName = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME,
                        new MultibranchPipelineProjectPage(getDriver()))
                .clickDisableEnableMultibranchPipeline()
                .getDisableMultibranchPipelineButtonText();

        Assert.assertEquals(buttonName, "Disable Multibranch Pipeline");
    }

    @Ignore
    @Test(dependsOnMethods = "testMultibranchPipelineEnable")
    public void testDeleteProject() {
        boolean itemIsDeleted = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME,
                        new MultibranchPipelineProjectPage(getDriver()))
                .clickDeleteButton()
                .confirmDeleteButton()
                .isItemDeleted(MULTI_PIPELINE_NAME);

        Assert.assertTrue(itemIsDeleted);
    }
}
