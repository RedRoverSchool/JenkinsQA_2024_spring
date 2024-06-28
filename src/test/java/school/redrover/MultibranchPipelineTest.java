package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.MultibranchPipelineConfigPage;
import school.redrover.model.MultibranchPipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("Multibranch Pipeline")
public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private static final String RENAMED_MULTI_PIPELINE = "NewMultibranchPipelineName";
    private static final String FOLDER_NAME = "NewFolder";

    @Test
    @Story("US_05.000  Create Multibranch Pipeline")
    @Description("Verify that a project can be created via the sidebar menu.")
    public void testCreateProjectViaSidebarMenu() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Project is displayed on Home page");
        Assert.assertListContainsObject(itemList, MULTI_PIPELINE_NAME, "Item is not found");
    }

    @Test
    @Story("US_05.000  Create Multibranch Pipeline")
    @Description("Verify error message is displayed when creating a project without a name")
    public void testCreateProjectWithEmptyName() {
        final String expectedErrorText = "» This field cannot be empty, please enter a valid name";

        String errorName = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipelineAndClickOk()
                .getErrorRequiresName();

        Allure.step("Expected result: Error message: " + expectedErrorText + "is displayed");
        Assert.assertEquals(errorName, expectedErrorText);
    }

    @Test
    @Story("US_05.007  Create Multibranch Pipeline from other existing")
    @Description("Verify creation by copying from other Multibranch Pipeline")
    public void testCreateProjectFromExistingMultibranchPipeline() {
        final String firstProjectName = "My first Multibranch Pipeline";
        final String secondItemName = "My second Multibranch Pipeline";

        TestUtils.createMultibranchProject(this, firstProjectName);

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(secondItemName)
                .typeItemNameInCopyFrom(firstProjectName)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Project from existing is displayed on Home page");
        Assert.assertListContainsObject(itemList, secondItemName,
                "Copy of " + firstProjectName + "not created!");
    }

    @Test
    @Story("US_05.004 Disable Multibranch Pipeline")
    @Description("Verify a project can be disabled via toggle.")
    public void testDisableProjectViaToggle() {
        final String expectedWarningText = "This Multibranch Pipeline is currently disabled";

        String disableWarningText = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggleToDisable()
                .clickSaveButton()
                .getTextOfDisableMultibranchPipeline();

        Allure.step("Expected result: " + expectedWarningText + " is displayed");
        Assert.assertEquals(disableWarningText, expectedWarningText);
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    @Story("US_05.004 Disable Multibranch Pipeline")
    @Description("Verify a project can be disabled via Disable Project Button.")
    public void testDisabledProjectViaDisableProjectButton() {
        final String expectedWarningText = "This Multibranch Pipeline is currently disabled";

        String disabledMessage = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .clickDisableProjectButton()
                .getTextOfDisableMultibranchPipeline();

        Allure.step("Expected result: " + expectedWarningText + " is displayed");
        Assert.assertEquals(disabledMessage, expectedWarningText);
    }

    @Test(dependsOnMethods = "testDisabledProjectViaDisableProjectButton")
    @Story("US_05.004 Disable Multibranch Pipeline")
    @Description("Verify that the color of the disabled project message")
    public void testVerifyProjectDisabledMessageColorOnStatusPage() {
        final String expectedColor = "rgba(254, 130, 10, 1)";

        String disabledMessageColor = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .getColorOfDisableMultibranchPipelineText();

        Allure.step("Expected result: The disabled project message color is " + expectedColor);
        Assert.assertEquals(disabledMessageColor, expectedColor);
    }

    @Test(dependsOnMethods = "testVerifyProjectDisabledMessageColorOnStatusPage")
    @Story("US_05.003  Enable Multibranch pipeline")
    @Description("Verify that a previously disabled project can be enabled back "
            + "by checking that disable button is displayed")
    public void testEnableProject() {
        String disableMultibranchPipelineButtonText = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickEnableButton()
                .getDisableMultibranchPipelineButtonText();

        Allure.step("Expected result:'Disable Multibranch Pipeline' button is displayed");
        Assert.assertEquals(disableMultibranchPipelineButtonText, "Disable Multibranch Pipeline");
    }

    @Test(dependsOnMethods = "testEnableProject")
    @Story("US_05.004 Disable Multibranch Pipeline")
    @Description("Verify the correct tooltip text is displayed when hovering over the toggle button")
    public void testDisabledTooltip() {
        final String tooltipText = "(No new builds within this Multibranch Pipeline "
                + "will be executed until it is re-enabled)";

        MultibranchPipelineConfigPage multibranchPipelineConfigPage = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigure()
                .hoverOverToggle();

        Allure.step("Expected result: Toggle has tooltip " + tooltipText);
        Assert.assertTrue(multibranchPipelineConfigPage.isTooltipDisplayed());
        Assert.assertEquals(multibranchPipelineConfigPage.getTooltipText(), tooltipText);
    }

    @Test
    @Story("US_05.004 Disable Multibranch Pipeline")
    @Description("Verify that the status toggle of a project reflects that the project is disabled.")
    public void testDisabledProjectToggleStatus() {
        String statusToggle = new HomePage(getDriver()).clickCreateAJob()
                .typeItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggleToDisable()
                .clickSaveButton()
                .clickConfigure()
                .getStatusToggle();

        Allure.step("Expected result: The status toggle reflects that the project is disabled");
        Assert.assertEquals(statusToggle, "false");
    }

    @Test(dependsOnMethods = "testDisabledProjectToggleStatus")
    @Story("US_05.003  Enable Multibranch pipeline")
    @Description("Verify the status toggle of a project reflects that the project is enable.")
    public void testEnableProjectToggleStatus() {

        String statusToggle = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .clickEnableButton()
                .clickConfigure()
                .getStatusToggle();

        Allure.step("Expected result: The status toggle reflects that the project is enabled");
        Assert.assertEquals(statusToggle, "true");
    }

    @Test
    @Story("US_05.001  Rename Multibranch pipeline")
    @Description("Verify a project can be successfully renamed via the sidebar")
    public void testRenameMultibranchPipelineViaSideBar() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickRenameOnSidebar()
                .clearNameInputField()
                .typeNewName(RENAMED_MULTI_PIPELINE)
                .clickRenameButtonWhenRenamedViaSidebar()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Renamed Project is displayed on Home Page");
        Assert.assertListContainsObject(itemList, RENAMED_MULTI_PIPELINE, "Item is not renamed successfully");
    }

    @Test
    @Story("US_05.001  Rename Multibranch pipeline")
    @Description("Verify error message is displayed when attempting to rename project to the same name")
    public void testRenameProjectWithNameSameAsCurrent() {
        final String expectedErrorMessage = "The new name is the same as the current name.";

        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String errorMessage = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(MULTI_PIPELINE_NAME)
                .clickRenameOnSidebar()
                .clearNameInputField()
                .typeNewName(MULTI_PIPELINE_NAME)
                .clickRenameButtonWhenRenamedViaSidebar()
                .getErrorText();

        Allure.step("Expected result: Error: " + expectedErrorMessage + " is displayed");
        Assert.assertEquals(errorMessage, expectedErrorMessage);
    }

    @Test
    @Story("US_05.001  Rename Multibranch pipeline")
    @Description("Verifies a project can be successfully renamed via dropdown menu")
    public void testRenameProjectViaMainPageDropdownMenu() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> itemList = new HomePage(getDriver())
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickRenameOnDropdown()
                .clearNameInputField()
                .typeNewName(RENAMED_MULTI_PIPELINE)
                .clickRenameButtonWhenRenamedViaDropdown(new MultibranchPipelineProjectPage(getDriver()))
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Renamed Project is displayed on Home Page");
        Assert.assertListContainsObject(itemList, RENAMED_MULTI_PIPELINE, "Item is not renamed successfully");
    }

    @Test
    @Story("US_05.002  View Multibranch pipeline page > Sidebar > Visibility, clickability, redirection")
    @Description("Verify list of sidebar tasks for a project created within a folder.")
    public void testProjectSidebarTasksUponCreatingViaFolder() {
        final List<String> sidebarTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events", "Delete Multibranch Pipeline", "People", "Build History", "Move",
                "Rename", "Pipeline Syntax", "Credentials");

        TestUtils.createFolderProject(this, FOLDER_NAME);

        MultibranchPipelineProjectPage multibranchPipelineProjectPage = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .clickNewItemOnSidebar()
                .typeItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton();

        Allure.step("Expected result: Sidebar tasks list should match " + sidebarTasks);
        Assert.assertEquals(multibranchPipelineProjectPage.getSidebarTasksSize(), 11);
        Assert.assertEquals(multibranchPipelineProjectPage.getSidebarTasksListHavingExistingFolder(), sidebarTasks);
    }

    @Test
    @Story("US_05.002  View Multibranch pipeline page > Sidebar > Visibility, clickability, redirection")
    @Description("Verify list of sidebar tasks for a project.")
    public void testVerifyProjectSidebarMenuList() {
        final List<String> expectedSidebarList =
                List.of("Status", "Configure", "Scan Multibranch Pipeline Log",
                        "Multibranch Pipeline Events", "Delete Multibranch Pipeline", "People",
                        "Build History", "Rename", "Pipeline Syntax", "Credentials");

        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        List<String> multibranchPipelineSidebarList = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .getSidebarTasksListHavingExistingFolder();

        Allure.step("Expected result: Sidebar tasks list should match " + expectedSidebarList);
        Assert.assertEquals(multibranchPipelineSidebarList.size(), 10);
        Assert.assertEquals(multibranchPipelineSidebarList, expectedSidebarList);
    }

    @Test
    @Story("US_05.006  Move Multibranch Pipeline")
    @Description("Verify that a project can be successfully moved to a folder via sidebar menu.")
    public void testMoveProjectToFolderViaSidebarMenu() {
        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String nestedMultibranchPipeline = new HomePage(getDriver())
                .clickSpecificPipelineName(MULTI_PIPELINE_NAME)
                .clickMoveOnSidebar()
                .selectDestinationFolderFromList(FOLDER_NAME)
                .clickMoveButtonWhenMovedViaSidebar()
                .clickFolderNameOnBreadcrumbs(FOLDER_NAME)
                .getNestedProjectName();

        Allure.step("Expected result: Nested folder is displayed on Folder Project Page");
        Assert.assertEquals(nestedMultibranchPipeline, MULTI_PIPELINE_NAME,
                MULTI_PIPELINE_NAME + "is not moved successfully");
    }

    @Test
    @Story("US_05.005  Delete Multibranch Pipeline")
    @Description("Verify the deletion of Multibranch Pipeline via dropdown menu.")
    public void testDeleteViaDashboardDropdown() {
        final String pageTitle = "Welcome to Jenkins!";

        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        String actualPageHeading = new HomePage(getDriver())
                .openItemDropdown(MULTI_PIPELINE_NAME)
                .clickDeleteOnDropdown()
                .clickYesForConfirmDelete()
                .getHeadingText();

        Allure.step("Expected result: Title " + pageTitle + "is displayed, indicating no projects exist");
        Assert.assertEquals(actualPageHeading, pageTitle);
    }

    @Test(dependsOnMethods = "testRenameMultibranchPipelineViaSideBar")
    @Story("US_05.005  Delete Multibranch Pipeline")
    @Description("Verify the deletion of Multibranch Pipeline via Breadcrumbs.")
    public void testDeleteViaBreadcrumbs() {
        boolean isProjectDeleted = new HomePage(getDriver())
                .clickSpecificMultibranchPipelineName(RENAMED_MULTI_PIPELINE)
                .clickBreadcrumbsArrowAfterProjectName(RENAMED_MULTI_PIPELINE)
                .clickDeleteOnBreadcrumbsMenu()
                .clickYesWhenDeletedItemOnHomePage()
                .isItemDeleted(RENAMED_MULTI_PIPELINE);

        Allure.step("Expected result: Project is not displayed on Home Page");
        Assert.assertTrue(isProjectDeleted, RENAMED_MULTI_PIPELINE + " was not deleted");
    }

    @Test
    @Story("US_05.005  Delete Multibranch Pipeline")
    @Description("Verify the deletion of a Multibranch pipeline via Sidebar menu.")
    public void testDeleteViaSidebarMenu() {
        TestUtils.createMultibranchProject(this, MULTI_PIPELINE_NAME);

        boolean isItemDeleted = new HomePage(getDriver())
                .clickSpecificPipelineName(MULTI_PIPELINE_NAME)
                .clickDeleteOnSidebar()
                .clickYesWhenDeletedItemOnHomePage()
                .isItemDeleted(MULTI_PIPELINE_NAME);

        Allure.step("Expected result: Project is not displayed on Home Page");
        Assert.assertTrue(isItemDeleted);
    }
}
