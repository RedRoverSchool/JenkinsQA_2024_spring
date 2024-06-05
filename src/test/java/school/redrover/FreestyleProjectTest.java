package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "Freestyle_Project_Name";
    private static final String RENAMED_FREESTYLE_PROJECT_NAME = "Renamed_Freestyle_Project";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String EDITED_PROJECT_DESCRIPTION = "Project new description";
    private static final String FOLDER_NAME = "Folder_Project_Name";

    @Test
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Verify that a new project can be created via Sidebar menu.")
    public void testCreateProjectViaSidebarMenu() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Verify that a new project can be created from an existing project.")
    public void testCreateProjectFromOtherExisting() {
        final String projectName1 = "Race Cars";
        final String projectName2 = "Vintage Cars";

        TestUtils.createFreestyleProject(this, projectName1);

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName2)
                .setItemNameInCopyForm(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(projectList, projectName2, "Project is not created from other existing");
    }

    @DataProvider
    public Object[][] provideUnsafeCharacters() {

        return new Object[][]{
                {"#"}, {"&"}, {"?"}, {"!"}, {"@"}, {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"\\"}, {"<"}, {">"},
                {"["}, {"]"}, {":"}, {";"}
        };
    }

    @Test(dataProvider = "provideUnsafeCharacters")
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Verify error message for project creation with invalid characters.")
    public void testCreateProjectInvalidCharsGetMassage(String unsafeChar) {
        String errorMassage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMassage, "» ‘" + unsafeChar + "’ is an unsafe character");

    }

    @Test(dataProvider = "provideUnsafeCharacters")
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Check OK button disabled for invalid project names.")
    public void testCreateProjectInvalidCharsDisabledOkButton(String unsafeChar) {
        boolean enabledOkButton = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .isOkButtonEnabled();

        Assert.assertFalse(enabledOkButton);
    }

    @Test
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Check error when create the project with an empty name.")
    public void testCreateProjectEmptyName() {
        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("   ")
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Check error for project creation with a name exceeding character limit.")
    public void testCreateProjectWithLongestName() {
        String projectName2 = "a".repeat(260);

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName2)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("Logging ID="));
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Check error when create the project with the same name.")
    public void testCreateProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorMessage.contains("A job already exists with the name"));
    }

    @Test
    @Epic("New item")
    @Story("US_00.007 Create a new item from other existing")
    @Description("Verify project can be copied from a container.")
    public void testCopyFromContainer() {
        String oldProjectName1 = "Race Cars";
        String newProjectName = "Vintage Cars";

        TestUtils.createFreestyleProject(this, oldProjectName1);

        List<String> elementsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newProjectName)
                .setItemNameInCopyForm(oldProjectName1)
                .clickOkAnyway(new FreestyleConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(elementsList, newProjectName, "Item is not found");
    }


    @Test
    @Epic("New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Verify configuration page open when the project is created.")
    public void testOpenConfigurePageOfProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String headerText = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickConfigure()
                .getHeaderSidePanelText();

        Assert.assertEquals(
                headerText,
                "Configure",
                "Configure page of the project is not opened");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.006 Move to Folder")
    @Description("Verify moving the project to a folder via the sidebar.")
    public void testMoveFreestyleProjectToFolderViaSideBar() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        String actualText = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickMove()
                .choosePath(FOLDER_NAME)
                .clickMoveButton()
                .getFullProjectPath();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.006 Move to Folder")
    @Description("Verify moving the project to a folder via the dropdown menu.")
    public void testProjectMovedToFolderViaDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        List<String> projectList = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickMoveInDropdown()
                .chooseFolderAndConfirmMove(FOLDER_NAME)
                .clickLogo()
                .clickJobByName(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .getItemListInsideFolder();

        Assert.assertListContainsObject(projectList, FREESTYLE_PROJECT_NAME, "Item is not moved successfully");
    }

    @Test(dependsOnMethods = "testProjectMovedToFolderViaDropdown")
    @Epic("Freestyle project")
    @Story("US_01.006 Move to Folder")
    @Description("Verify via breadcrumbs moving the project to a folder.")
    public void testCheckFreestyleProjectViaBreadcrumb() {
        List<String> itemListInsideFolder = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .getHeader().clickMyViewsOnHeaderDropdown()
                .clickBreadcrumbAll()
                .clickJobNameBreadcrumb(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.001 Add description")
    @Description("Verify adding a description to project by 'Add Description' button.")
    public void testAddDescriptionUsingAddDescriptionButton() {
        String projectDescription = new HomePage(getDriver())
                .clickNewItem().setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickAddDescription()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertTrue(projectDescription.matches(FREESTYLE_PROJECT_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testAddDescriptionUsingAddDescriptionButton")
    @Epic("Freestyle project")
    @Story("US_01.005 Edit description Project")
    @Description("Verify the edited description is visible.")
    public void testEditProjectDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescription()
                .clearDescription()
                .setDescription(EDITED_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, EDITED_PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditProjectDescription")
    @Epic("Freestyle project")
    @Story("US_01.007 Delete description Project")
    @Description("Verify deleting the description of the project.")
    public void testDeleteProjectDescription() {
        boolean addDescriptionButtonEnable = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .isAddDescriptionButtonEnable();

        Assert.assertTrue(addDescriptionButtonEnable);
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.003 Disable/Enable Project")
    @Description("Disable project.")
    public void testDisableProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String disabledStatus = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisableProjectButton()
                .getDesabledMassageText();

        Assert.assertTrue(disabledStatus.contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testDisableProject")
    @Epic("Freestyle project")
    @Story("US_01.003 Disable/Enable Project")
    @Description("Enable disabled project.")
    public void testEnableProject() {
        String disableButtonText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEnableButton()
                .getDisableProjectButtonText();

        Assert.assertEquals(disableButtonText, "Disable Project");
    }

    @Test(dependsOnMethods = "testEnableProject")
    @Epic("Freestyle project")
    @Story("US_01.008 Build now Project.")
    @Description("Verify that the project can be successfully built")
    public void testBuildNowProject() {
        String actualResult = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearience()
                .getBuildInfo();

        Assert.assertEquals(actualResult, "#1");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.002 Rename project")
    @Description("Verify that the project can be successfully renamed via Sidebar Menu")
    public void testRenameProjectViaSidebarMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .setNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, RENAMED_FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.002 Rename project")
    @Description("Verify that the project can be successfully renamed via Dropdown Menu")
    public void testRenameProjectViaDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String projectName = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameOnDropdownForFreestyleProject()
                .setNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRename()
                .getProjectName();

        Assert.assertEquals(projectName, RENAMED_FREESTYLE_PROJECT_NAME);
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.002 Rename project")
    @Description("Verify that the project can be successfully renamed via Breadcrumbs Dropdown Menu")
    public void testRenameProjectViaBreadcrumbsDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBreadcrumbsDropdownArrow()
                .clickBreadcrumbsDropdownRenameProject(FREESTYLE_PROJECT_NAME)
                .setNewItemName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRename(new FreestyleProjectPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, RENAMED_FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.002 Rename project")
    @Description("Check error when rename the project with empty name")
    public void testDropdownRenameWithEmptyName() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String errorMassage = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameOnDropdownForFreestyleProject()
                .clearNameAndClickRenameButton()
                .getMessageText();

        Assert.assertEquals(errorMassage, "No name is specified");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.004 Delete project")
    @Description("Delete project from project's page left side panel")
    public void testDeleteProjectViaSidebar() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> projectList = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getItemList();

        Assert.assertTrue(projectList.isEmpty());
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.004 Delete project")
    @Description("Delete project from breadcrumb navigation menu on project's page")
    public void testGetWelcomePageWhenDeleteProjectViaBreadCrumbMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBreadcrumbsDropdownArrow()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getHeadingText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }

    @Test
    @Epic("Freestyle project")
    @Story("US_01.004 Delete project")
    @Description("Delete project from drop-down menu")
    public void testDeleteProjectDropdownMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        boolean isItemDeleted = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickDeleteOnDropdownAndConfirm()
                .isItemDeleted(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(isItemDeleted);
    }
}
