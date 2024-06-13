package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

@Epic("Multi-configuration project")
public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MCProject";
    private static final String RANDOM_PROJECT_NAME = TestUtils.randomString();
    private static final String FOLDER_NAME = "Folder_name";

    private String generateRandomNumber() {
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }


    @Test
    @Story("US_03.004  Rename project")
    @Description("Check, an existing project can be renamed via dropdown")
    public void testRenameProjectViaDashboardDropdown() {
        final String addToProjectName = "New";
        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String newProjectName = new HomePage(getDriver())
                .openItemDropdownWithSelenium(PROJECT_NAME)
                .clickRenameOnDropdownForMultiConfigurationProject()
                .changeProjectNameWithoutClear(addToProjectName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(newProjectName,
                "Project " + PROJECT_NAME + "New",
                "Project name has not been changed");
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Adding the project description")
    public void testAddDescription() {
        final String TEXT = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        String description = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME)
                .clickSpecificMultiConfigurationProjectName(RANDOM_PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(TEXT)
                .clickSaveDescription()
                .getDescriptionText();

        Assert.assertEquals(description, TEXT);
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Add some text above to existing description of the project")
    public void testEditDescriptionWithoutDelete() {
        final String text = "qwerty123";
        final String additionalText = "AAA";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        String DescriptionText = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(text)
                .clickSaveDescription()
                .clickLogo()
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(additionalText)
                .clickSaveDescription()
                .getDescriptionText();

        Assert.assertEquals(DescriptionText, additionalText + text);
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Check, the text in a preview field equals to the text in the description field")
    public void testDescriptionPreview() {
        final String text = "I want to see preview";

        String previewText =
                TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                        .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                        .clickAddDescriptionButton()
                        .addOrEditDescription(text)
                        .clickPreview()
                        .getPreviewText();

        Assert.assertEquals(previewText, text);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Check creating project by copying the exist one")
    public void testMakeCopyMultiConfigurationProject() {
        final String newProjectName = "MCProject copy";
        List<String> projectList = TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickNewItem()
                .setItemName(newProjectName)
                .typeItemNameInCopyFrom(PROJECT_NAME)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectList.contains(newProjectName));
    }

    @Test
    @Story("US_03.001  Add/edit description")
    @Description("Delete project description")
    public void testDeleteProjectDescription() {
        final String DESCRIPTION_TEXT = "This is project description";
        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        MultiConfigurationProjectPage multiConfigurationProjectPage = new MultiConfigurationProjectPage(getDriver());

        boolean isDescriptionDeleted = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .addOrEditDescription(DESCRIPTION_TEXT)
                .clickSaveDescription()
                .clickLogo()
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .clearDescription()
                .clickSaveDescription()
                .isDescriptionEmpty();

        Assert.assertTrue(isDescriptionDeleted);
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Disable project by toggle")
    public void testProjectDisableByToggle() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        boolean statusToggle = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .clickToggleSwitch()
                .clickApply()
                .getStatusToggle();

        Assert.assertFalse(statusToggle);
    }

    @Test(dependsOnMethods = "testProjectDisableByToggle")
    @Story("US_03.002  Enable/Disable project")
    @Description("Get tooltip information 'Enable or disable the current project' by hovering on a Toggle Switch")
    public void testCheckTooltipEnablingMultiConfigurationProject() {

        String toggleTooltipText = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .hoverOverToggleSwitch()
                .getToggleTooltipText();

        Assert.assertEquals(toggleTooltipText, "Enable or disable the current project");
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Checking 'Yes' button color when delete project")
    public void testYesButtonColorDeletingMultiConfigurationProjectInSidebar() {
        final String expectedColorNone = "#e6001f";

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String actualYesButtonColor = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickSidebarDelete()
                .getYesButtonColorDeletingViaSidebar();

        Assert.assertEquals(actualYesButtonColor, expectedColorNone);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Create project with empty name")
    public void testCreateProjectWithoutName() {
        final String EMPTY_NAME = "";
        final String ERROR_MESSAGE = "This field cannot be empty";

        CreateNewItemPage createNewItemPage =
                new HomePage(getDriver())
                        .clickNewItem()
                        .setItemName(EMPTY_NAME)
                        .selectMultiConfiguration();

        boolean isErrorMessageCorrect = createNewItemPage.getErrorMessageEmptyName().contains(ERROR_MESSAGE);
        boolean isCanNotPressOkButton = createNewItemPage.isOkButtonNotActive();

        Assert.assertTrue(isErrorMessageCorrect && isCanNotPressOkButton);
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Create project with valid name")
    public void testCreateMultiConfigurationProject() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectNameList.contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProject")
    @Story("US_03.004  Rename project")
    @Description("Check,an existing project can be renamed")
    public void testRenameMCProject() {

        HomePage homePage = new HomePage(getDriver());

        homePage
                .clickJobByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickRenameInMenu()
                .changeProjectNameWithClear(RANDOM_PROJECT_NAME)
                .clickRenameButton()
                .clickLogo();

        Assert.assertTrue(homePage.isItemExists(RANDOM_PROJECT_NAME) && !homePage.isItemExists(PROJECT_NAME));
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Delete an existing project via Dropdown menu")
    public void testDeleteProjectViaDropdown() {

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        List<String> itemsList = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickBreadcrumbsProjectDropdownArrow()
                .clickDropdownDelete()
                .clickYes(new HomePage(getDriver()))
                .getItemList();

        Assert.assertListNotContainsObject(itemsList, PROJECT_NAME,
                "Project not deleted");
    }

    @Test
    @Story("US_03.005  Move project to Folder")
    @Description("Move the project to the Folder via Dropdown menu")
    public void testMoveProjectToFolderViaDropdown() {
        final String folderName = "Folder";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        TestUtils.createNewItem(this, folderName, TestUtils.Item.FOLDER);

        Assert.assertTrue(new HomePage(getDriver())
                .openItemDropdownWithSelenium(PROJECT_NAME)
                .selectMoveFromDropdown()
                .selectFolder(folderName)
                .clickMove()
                .isProjectInsideFolder(PROJECT_NAME, folderName));
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("Delete an existing project via left-sidebar menu")
    public void testDeleteMultiConfigurationProjectFromMenu() {

        HomePage homePage = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME);

        boolean isProjectDeleted = homePage
                .clickJobByName(RANDOM_PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickSidebarDelete()
                .clickYes(homePage)
                .isItemDeleted(RANDOM_PROJECT_NAME);

        Assert.assertTrue(isProjectDeleted);
    }

    @Test
    @Story("US_03.006  Edit configuration")
    @Description("Add discard old builds configurations to project")
    public void testAddDiscardOldBuildsConfigurationsToProject() {
        final String daysToKeep = generateRandomNumber();
        final String numToKeep = generateRandomNumber();
        final String artifactDaysToKeep = generateRandomNumber();
        final String artifactNumToKeep = generateRandomNumber();

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        List<String> discardOldBuildsList = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickConfigureButton()
                .clickDiscardOldBuilds()
                .enterNumberOfDaysToKeepBuilds(daysToKeep)
                .enterMaxNumberOfBuildsToKeep(numToKeep)
                .clickAdvancedButton()
                .enterNumberOfDaysToKeepArtifacts(artifactDaysToKeep)
                .enterMaxNumberOfBuildsToKeepWithArtifacts(artifactNumToKeep)
                .clickSaveButton()
                .clickConfigureButton()
                .clickAdvancedButton()
                .getDiscardOldBuildsListText();

        Allure.step("Expected result : Values of discard old builds parameters are the same as entered");
        Assert.assertEquals(
                discardOldBuildsList,
                List.of(daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep));
    }

    @Test
    @Story("US_03.000 Create project")
    @Description("Verify existing project can be found using Search")
    public void testSearchForCreatedProject() {

        SearchResultPage searchResultPage = TestUtils
                .createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .getHeader()
                .typeTextToSearchField(PROJECT_NAME)
                .getHeader()
                .pressEnterOnSearchField();

        String currentUrl = searchResultPage.getCurrentUrl();
        String searchResult = searchResultPage.getTextFromMainPanel();

        Assert.assertTrue(currentUrl.contains(PROJECT_NAME) && searchResult.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Check, that there is a special icon near displayed project on Dashboard")
    public void testVerifyThatDisabledIconIsDisplayedOnDashboard() {

        List<String> disabledProjectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickBreadcrumbsProjectName()
                .clickDisableProject()
                .clickLogo()
                .getDisabledProjectListText();

        Assert.assertTrue(disabledProjectList.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_03.005  Move project to Folder")
    @Description("Move project to Folder using left-sidebar  menu on Dashboard")
    public void testMoveProjectToFolderFromDashboardPage() {

        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickMoveOptionInMenu()
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME);

        boolean isProjectMoved = new FolderProjectPage(getDriver()).getItemListInsideFolder().contains(PROJECT_NAME);
        boolean isProjectDeleted = new HomePage(getDriver()).isItemDeleted(FOLDER_NAME);

        Assert.assertTrue(isProjectMoved && isProjectDeleted);
    }

    @Test
    @Story("US_03.002  Enable/Disable project")
    @Description("Check, that disable Message appears, when a project is disabled")
    public void testDisableProjectOnProjectPage() {

        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String disableMessage = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickDisableProject()
                .getDisableMessage();

        Assert.assertTrue(disableMessage.contains("This project is currently disabled"), "Substring not found");
    }

    @Test(dependsOnMethods = "testDisableProjectOnProjectPage")
    @Story("US_03.002  Enable/Disable project")
    @Description("Check,that 'Enable' status is indicated on Project Configure Page, when a project is enable")
    public void testEnableProjectOnProjectPage() {

        String enableMessage = new HomePage(getDriver())
                .clickSpecificMultiConfigurationProjectName(PROJECT_NAME)
                .clickEnableButton()
                .clickConfigureButton()
                .getToggleStatusMessage();

        Assert.assertTrue(enableMessage.matches("Enabled"),
                "Substring not found");
    }

}