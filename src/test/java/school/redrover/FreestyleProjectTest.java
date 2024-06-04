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
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
    public void testCreateProject() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, FREESTYLE_PROJECT_NAME, "Item is not found");
    }

    @Test
    @Epic("00 New item")
    @Story("US_00.007 Create a new item from other existing")
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
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
    public void testCreateProjectInvalidCharsGetMassage(String unsafeChar) {
        String errorMassage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMassage, "» ‘" + unsafeChar + "’ is an unsafe character");

    }

    @Test(dataProvider = "provideUnsafeCharacters")
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
    public void testCreateProjectInvalidCharsDisabledOkButton(String unsafeChar) {
        boolean enabledOkButton = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .isOkButtonEnabled();

        Assert.assertFalse(enabledOkButton);
    }

    @Test
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
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
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
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

    @Test
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
    public void testCreateProjectWithSpecialSymbol() {
        String projectName2 = TestUtils.getUniqueName("testproject/");

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName2)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test(dependsOnMethods = "testCreateProject")
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
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
    @Epic("00 New item")
    @Story("US_00.007 Create a new item from other existing")
    public void testCopyFromContainer() {
        String oldProjectName1 = "Race Cars";
        String newProjectName = "Vintage Cars";

        TestUtils.createFreestyleProject(this, oldProjectName1);

        List<String> elementsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(newProjectName)
                .setItemNameInCopyForm(oldProjectName1.substring(0, 1))
                .getCopyFormElementsList();

        Assert.assertTrue(elementsList.contains(oldProjectName1));
    }

    @Test
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.006 Move Project to Folder")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.006 Move Project to Folder")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.006 Move Project to Folder")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.001 Add description")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.001 Add description")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.001 Add description")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.003 Disable/Enable Project")
    public void testDisableProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String disabledStatus = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisableProjectButton()
                .getDesabledMassageText();

        Assert.assertTrue(disabledStatus.contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testDisableProject")
    @Epic("01_FreestyleProject")
    @Story("US_01.003 Disable/Enable Project")
    public void testEnableProject() {
        String disableButtonText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEnableButton()
                .getDisableProjectButtonText();

        Assert.assertEquals(disableButtonText, "Disable Project");
    }

    @Test(dependsOnMethods = "testEnableProject")
    @Epic("01_FreestyleProject")
    @Story("US_01.007 Build now")
    public void testBuildNowProject() {
        String actualResult = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearience()
                .getBuildInfo();

        Assert.assertEquals(actualResult, "#1");
    }


    @Test
    @Epic("01_FreestyleProject")
    @Story("US_01.002 Rename Project")
    public void testRenameProjectViaSideBarMenu() {
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
    @Epic("01_FreestyleProject")
    @Story("US_01.002 Rename Project")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.002 Rename Project")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.002 Rename Project")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.004 Delete Project")
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
    @Epic("01_FreestyleProject")
    @Story("US_01.004 Delete Project")
    public void testGetWelcomePageWhenDeleteProjectViaSideBar() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getHeadingText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }

    @Test
    @Epic("01_FreestyleProject")
    @Story("US_01.004 Delete Project")
    public void testDeleteUsingDropdown() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        int beforeSize = new HomePage(getDriver()).getItemList().size();

        List<String> itemList = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .getItemList();

        Assert.assertEquals(beforeSize - 1, itemList.size());
    }

    @Test
    @Epic("01_FreestyleProject")
    @Story("US_01.004 Delete Project")
    public void testDeleteProjectDropdownMenu() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        boolean isItemDeleted = new HomePage(getDriver())
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickDeleteOnDropdownAndConfirm()
                .isItemDeleted(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(isItemDeleted);
    }

    @Test
    @Epic("00 New item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("Verify freestyle project config page question marks tooltips")
    public void testCheckQuestion() {

        List<String> expectedList = List.of("Help for feature: Discard old builds",
                "Help for feature: This project is parameterized",
                "Help for feature: Throttle builds",
                "Help for feature: Execute concurrent builds if necessary",
                "Help for feature: Git",
                "Help for feature: Trigger builds remotely (e.g., from scripts)",
                "Help for feature: Build after other projects are built",
                "Help for feature: Build periodically",
                "Help for feature: GitHub hook trigger for GITScm polling",
                "Help for feature: Poll SCM",
                "Help for feature: Use secret text(s) or file(s)",
                "Help for feature: With Ant");

        List<String> actualList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .getTooltipList();

        Assert.assertEquals(actualList,expectedList);

    }
}
