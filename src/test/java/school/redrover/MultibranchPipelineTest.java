package school.redrover;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private static final String RENAMED_MULTI_PIPELINE = "NewMultibranchPipelineName";
    private static final String FOLDER_NAME = "NewFolder";

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
                .setItemNameInCopyFrom(FIRST_ITEM_NAME)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, SECOND_ITEM_NAME,
                "Copy of " + FIRST_ITEM_NAME + "not created!");
    }

    @Test
    public void testChangeFromDisabledToEnabledOnStatusPage() {
        MultibranchPipelineProjectPage multibranchPipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .clickEnableButton();

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
                .clickEnableButton()
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
    public void testRenameMultibranchPipelineViaSideBar() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
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
    public void testRenameMultibranchPipelineViaMainPageDropdownMenu() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> itemList = new HomePage(getDriver())
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickRenameOnDropdownForMultibranchPipeline()
                .clearNewNameInput()
                .setItemName(RENAMED_MULTI_PIPELINE)
                .clickRename(new MultibranchPipelineProjectPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, RENAMED_MULTI_PIPELINE, "Item is not renamed successfully");
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
    public void testVerifyProjectSidebarMenuList() {
        final List<String> expectedSidebarList =
                List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
                        "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");

        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> multibranchPipelineSidebarList = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .getProjectSidebarList();

        Assert.assertEquals(multibranchPipelineSidebarList.size(), 10);
        Assert.assertEquals(multibranchPipelineSidebarList, expectedSidebarList);
    }

    @Test
    public void testMoveProjectToFolderViaSidebarMenu() {
        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String nestedMultibranchPipeline = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickMoveOnSidebar(MULTI_PIPELINE_NAME)
                .chooseDestinationFromListAndMove(FOLDER_NAME)
                .clickLogo()
                .clickJobByName(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .getNestedProjectName();

        Assert.assertEquals(nestedMultibranchPipeline, MULTI_PIPELINE_NAME, MULTI_PIPELINE_NAME + "is not moved successfully");
    }

    @Test
    public void testDeleteViaDashboardDropdown() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String actualPageHeading = new HomePage(getDriver())
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .getHeadingText();

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

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    public void testVerifyMpDisabledOnStatusPage() {
        String disabledMessage = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .clickDisableProjectButton()
                .getDisableMultibranchPipelineText();

        Assert.assertEquals(disabledMessage, "This Multibranch Pipeline is currently disabled");
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledOnStatusPage")
    public void testVerifyMpDisabledMessageColorOnStatusPage() {
        String disabledMessageColor = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .getDisableMultibranchPipelineTextColor();

        Assert.assertEquals(disabledMessageColor, "rgba(254, 130, 10, 1)");
    }

    @Test(dependsOnMethods = "testRenameMultibranchPipelineViaSideBar")
    public void testDeleteMpViaBreadcrumbs() {
        boolean isMpDeleted = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(RENAMED_MULTI_PIPELINE)
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
                .clickEnableButton()
                .getDisableMultibranchPipelineButtonText();

        Assert.assertEquals(buttonName, "Disable Multibranch Pipeline");
    }

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
