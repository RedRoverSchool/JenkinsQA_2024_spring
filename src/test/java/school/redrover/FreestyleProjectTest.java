package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.ErrorPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("Freestyle project")
public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "Freestyle_Project_Name";
    private static final String RENAMED_FREESTYLE_PROJECT_NAME = "Renamed_Freestyle_Project";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String EDITED_PROJECT_DESCRIPTION = "Project new description";
    private static final String FOLDER_NAME = "Folder_Project_Name";

    @Test
    @Story("US_01.000  Create Project")
    @Description("Verify that a new project can be created via Sidebar menu.")
    public void testCreateProjectViaSidebarMenu() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Freestyle Project is displayed on Home page");
        Assert.assertListContainsObject(itemList, FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Story("US_01.000  Create Project")
    @Description("Verify that a new project can be created from an existing project.")
    public void testCreateProjectFromOtherExisting() {
        final String projectName1 = "Race Cars";
        final String projectName2 = "Vintage Cars";

        TestUtils.createFreestyleProject(this, projectName1);

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(projectName2)
                .typeItemNameInCopyFrom(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Freestyle Project from an existed project is displayed on Home page");
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
    @Story("US_01.000  Create Project")
    @Description("Verify error message for project creation with invalid characters.")
    public void testCreateProjectInvalidCharsGetMessage(String unsafeChar) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectFreeStyleProject()
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step("Expected result: Error message '» " + unsafeChar + "’ is an unsafe character' is displayed");
        Assert.assertEquals(errorMessage, "» ‘" + unsafeChar + "’ is an unsafe character");

    }

    @Test(dataProvider = "provideUnsafeCharacters")
    @Story("US_01.000  Create Project")
    @Description("Check OK button disabled for invalid project names.")
    public void testCreateProjectInvalidCharsDisabledOkButton(String unsafeChar) {
        boolean enabledOkButton = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectFreeStyleProject()
                .isOkButtonEnabled();

        Allure.step("Expected result: OK button is disabled for the invalid project name: " + unsafeChar);
        Assert.assertFalse(enabledOkButton);
    }

    @Test
    @Story("US_01.000  Create Project")
    @Description("Check error when create the project with an empty name.")
    public void testCreateProjectEmptyName() {
        String expectedErrorMessage = "No name is specified";

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("   ")
                .selectFreeStyleProject()
                .clickOkAnyway(new ErrorPage(getDriver()))
                .getErrorText();

        Allure.step("Expected result: Error message " + expectedErrorMessage + "is displayed");
        Assert.assertEquals(errorText, expectedErrorMessage);
    }

    @Test
    @Story("US_01.000  Create Project")
    @Description("Check error for project creation with a name exceeding character limit.")
    public void testCreateProjectWithLongestName() {
        final String expectedErrorMessage = "A problem occurred while processing the request";
        final String projectName = "a".repeat(260);

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(projectName)
                .selectFreeStyleProject()
                .clickOkAnyway(new ErrorPage(getDriver()))
                .getProblemText();

        Allure.step("Expected result: Error message " + expectedErrorMessage + "is displayed");
        Assert.assertEquals(errorText, expectedErrorMessage);
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    @Story("US_01.000  Create Project")
    @Description("Check error when create the project with the same name.")
    public void testCreateProjectWithDuplicateName() {
        final String expectedErrorMessage = "A job already exists with the name ";

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOkAnyway(new ErrorPage(getDriver()))
                .getErrorText();

        Allure.step("Expected result: Error Message " + expectedErrorMessage + "is displayed");
        Assert.assertEquals(errorMessage, expectedErrorMessage + "‘" + FREESTYLE_PROJECT_NAME + "’");
    }

    @Test
    @Story("US_01.000  Create Project")
    @Description("Verify configuration page open when the project is created.")
    public void testOpenConfigurePageOfProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String headerText = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickConfigure()
                .getHeaderSidePanelText();

        Allure.step("Expected results: The header text should be 'Configure'");
        Assert.assertEquals(headerText, "Configure", "Configure page of the project is not opened");
    }

    @Test
    @Story("US_01.005  Move Project")
    @Description("Move the project to a Folder via the sidebar")
    public void testMoveProjectToFolderViaSideBar() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        String actualText = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickMoveOnSidebar()
                .selectDestinationFolderFromList(FOLDER_NAME)
                .clickMoveButtonWhenMovedViaSidebar()
                .getFullProjectPath();

        Allure.step("Expected result: The full project path should contain: " + expectedText);
        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");
    }

    @Test
    @Story("US_01.005  Move Project")
    @Description("Move the project to a Folder via Dropdown")
    public void testMoveProjectToFolderViaDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        List<String> projectList = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickMoveOnDropdown()
                .selectDestinationFolderFromList(FOLDER_NAME)
                .clickMoveButtonWhenMovedViaDropdown(new FreestyleProjectPage(getDriver()))
                .clickFolderNameOnBreadcrumbs(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: The project list inside the folder contain moved project");
        Assert.assertListContainsObject(projectList, FREESTYLE_PROJECT_NAME, "Item is not moved successfully");
    }

    @Test(dependsOnMethods = "testMoveProjectToFolderViaDropdown")
    @Story("US_01.005  Move Project")
    @Description("Move the project to a Folder via breadcrumbs")
    public void testMoveProjectToFolderViaBreadcrumb() {
        List<String> itemListInsideFolder = new HomePage(getDriver())
                .getHeader().clickMyViewsOnHeaderDropdown()
                .clickAllDropdownMenuOnBreadcrumbs()
                .clickOnProjectNameOnBreadcrumbsMenu(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: The project list inside the folder contain moved project");
        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    @Story("US_01.006  Change project description")
    @Description("Add project description by 'Add Description' button")
    public void testAddDescriptionUsingAddDescriptionButton() {
        String projectDescription = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickAddDescription()
                .typeDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("The project description matches: " + FREESTYLE_PROJECT_DESCRIPTION);
        Assert.assertTrue(projectDescription.matches(FREESTYLE_PROJECT_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testAddDescriptionUsingAddDescriptionButton")
    @Story("US_01.006  Change project description")
    @Description("Add project description")
    public void testEditProjectDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescription()
                .clearDescription()
                .typeDescription(EDITED_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("Expected result: The project description should be updated to: " + EDITED_PROJECT_DESCRIPTION);
        Assert.assertEquals(projectDescriptionText, EDITED_PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditProjectDescription")
    @Story("US_01.006  Change project description")
    @Description("Delete project description")
    public void testDeleteProjectDescription() {
        boolean isAddDescriptionButtonEnable = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .isAddDescriptionButtonEnable();

        Allure.step("Expected result: The 'Add Description' button is enabled.");
        Assert.assertTrue(isAddDescriptionButtonEnable);
    }

    @Test
    @Story("US_01.003  Disable/Enable Project")
    @Description("Disable project")
    public void testDisableProject() {
        final String expectedWarningMessage = "This project is currently disabled";
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String disabledStatus = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisableProjectButton()
                .getDisabledMessageText();

        Allure.step("Expected result: The warning message '" + expectedWarningMessage + "' is displayed.");
        Assert.assertTrue(disabledStatus.contains(expectedWarningMessage));
    }

    @Test(dependsOnMethods = "testDisableProject")
    @Story("US_01.003  Disable/Enable Project")
    @Description("Enable disabled project")
    public void testEnableProject() {
        String expectedButtonText = "Disable Project";

        String disableButtonText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEnableButton()
                .getDisableProjectButtonText();

        Allure.step("Expected result: The button text is '" + expectedButtonText);
        Assert.assertEquals(disableButtonText, expectedButtonText);
    }

    @Test(dependsOnMethods = "testEnableProject")
    @Story("US_01.007  Build now Project")
    @Description("Verify that the project can be successfully built")
    public void testBuildNowProject() {
        String actualResult = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .getBuildInfo();

        Allure.step("Expected result: The build is successful and the build number shown");
        Assert.assertEquals(actualResult, "#1");
    }

    @Test
    @Story("US_01.002  Rename project")
    @Description("Rename project from project's page via left sidebar panel")
    public void testRenameProjectViaSidebarMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickRenameOnSidebar()
                .clearNameInputField()
                .typeNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRenameButtonWhenRenamedViaSidebar()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Renamed Freestyle Project is displayed on Home page");
        Assert.assertListContainsObject(itemList, RENAMED_FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Story("US_01.002  Rename project")
    @Description("Rename project via dropdown menu")
    public void testRenameProjectViaDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String projectName = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameOnDropdown()
                .clearNameInputField()
                .typeNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRenameButtonWhenRenamedViaDropdown(new FreestyleProjectPage(getDriver()))
                .getProjectName();

        Allure.step("Expected result: Renamed Freestyle Project is displayed");
        Assert.assertEquals(projectName, RENAMED_FREESTYLE_PROJECT_NAME);
    }

    @Test
    @Story("US_01.002  Rename project")
    @Description("Rename project via breadcrumb navigation menu on project's page")
    public void testRenameProjectViaBreadcrumbsDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> itemList = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickBreadcrumbsArrowAfterProjectName(FREESTYLE_PROJECT_NAME)
                .clickRenameOnBreadcrumbsMenu()
                .clearNameInputField()
                .typeNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRenameButtonWhenRenamedViaBreadcrumbs()
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Renamed Freestyle Project is displayed on Home page");
        Assert.assertListContainsObject(itemList, RENAMED_FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Story("US_01.002  Rename project")
    @Description("Check error when rename the project with empty name")
    public void testDropdownRenameWithEmptyName() {
        final String expectedErrorMessage = "No name is specified";

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String errorMessage = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameOnDropdown()
                .clearNameInputField()
                .clickRenameButtonWhenRenamedViaDropdown(new ErrorPage(getDriver()))
                .getErrorText();

        Allure.step("Expected result: error message" + expectedErrorMessage + "is displayed");
        Assert.assertEquals(errorMessage, expectedErrorMessage);
    }

    @Test
    @Story("US_01.004  Delete project")
    @Description("Delete project from project's page left sidebar panel")
    public void testDeleteProjectViaSidebar() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        List<String> projectList = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickDeleteOnSidebar()
                .clickYesWhenDeletedItemOnHomePage()
                .getItemList();

        Allure.step("Expected result: project list on Home Page is empty");
        Assert.assertTrue(projectList.isEmpty());
    }

    @Test
    @Story("US_01.004  Delete project")
    @Description("Delete project via breadcrumb navigation menu on project's page")
    public void testGetWelcomePageWhenDeleteProjectViaBreadCrumbMenu() {
        final String expectedHeader = "Welcome to Jenkins!";

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickSpecificFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickBreadcrumbsArrowAfterProjectName(FREESTYLE_PROJECT_NAME)
                .clickDeleteOnBreadcrumbsMenu()
                .clickYesWhenDeletedItemOnHomePage()
                .getHeadingText();

        Allure.step("Expected result: " + expectedHeader + "is displayed indicating there are no projects exists");
        Assert.assertEquals(welcomeJenkinsHeader, expectedHeader);
    }

    @Test
    @Story("US_01.004  Delete project")
    @Description("Delete project via dropdown menu")
    public void testDeleteProjectViaDropdownMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        boolean isItemDeleted = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickDeleteOnDropdown()
                .clickYesForConfirmDelete()
                .isItemDeleted(FREESTYLE_PROJECT_NAME);

        Allure.step("Expected result: the project is not displayed on Home Page");
        Assert.assertTrue(isItemDeleted);
    }
}
