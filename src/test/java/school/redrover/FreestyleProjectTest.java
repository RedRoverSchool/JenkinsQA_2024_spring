package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.model.*;
import school.redrover.runner.*;

import java.util.List;
import java.util.UUID;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "Freestyle_Project_Name";
    private static final String RENAMED_FREESTYLE_PROJECT_NAME = "Renamed_Freestyle_Project";
    private static final String FREESTYLE_PROJECT_DESCRIPTION = "Some description text";
    private static final String EDITED_PROJECT_DESCRIPTION = "Project new description";
    private static final String FOLDER_NAME = "Folder";

    @Test
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
    public void testCreateProjectFromOtherExisting() {
        final String projectName1 = "Race Cars";
        final String projectName2 = "Vintage Cars";

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(projectName2)
                .setItemNameInCopyForm(projectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectList.contains(projectName2));
    }

    @DataProvider
    public Object[][] provideUnsafeCharacters() {

        return new Object[][]{
                {"#"}, {"&"}, {"?"}, {"!"}, {"@"}, {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"\\"}, {"<"}, {">"},
                {"["}, {"]"}, {":"}, {";"}
        };
    }

    @Test(dataProvider = "provideUnsafeCharacters")
    public void testCreateProjectInvalidCharsGetMassage(String unsafeChar) {
        String errorMassage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(errorMassage, "» ‘" + unsafeChar + "’ is an unsafe character");

    }

    @Test(dataProvider = "provideUnsafeCharacters")
    public void testCreateProjectInvalidCharsDisabledOkButton(String unsafeChar) {
        boolean enabledOkButton = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeChar)
                .selectFreeStyleProject()
                .isOkButtonEnabled();

        Assert.assertFalse(enabledOkButton);
    }

    @Test
    public void testCreateProjectWithSpacesName() {
        String projectName2 = "     ";

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName2)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateProjectWithLongestName() {
        String projectName2 = "a".repeat(260);

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName2)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();
        System.out.println(errorText);

        Assert.assertTrue(errorText.contains("Logging ID="));
    }

    @Test
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
    public void testAddedProjectIsDisplayedOnTheDashboardPanel() {
        List<String> itemList = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(
                itemList.contains(FREESTYLE_PROJECT_NAME),
                "Project with '" + FREESTYLE_PROJECT_NAME + "' name is not in the list");
    }

    @Test
    public void testCreateProjectWithEmptyName() {
        Boolean errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .selectFreeStyleProject()
                .getOkButtoneState();

        Assert.assertFalse(errorMessage);
    }

    @Ignore
    @Test
    public void testCopyFromContainer() {
        String oldProjectName1 = "Race Cars";
        String oldProjectName2 = "Race Bikes";
        String newProjectName = "Vintage Cars";

        List<String> elementsList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(oldProjectName1)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(oldProjectName2)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(newProjectName)
                .setItemNameInCopyForm(oldProjectName1.substring(0, 1))
                .getCopyFormElementsList();

        Assert.assertTrue(elementsList.contains(oldProjectName1));
    }

    @Test
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

    @Test
    public void testEditDescriptionConfigurationSidebar() {
        final String projectDescription = "This test is trying to create a new freestyle job";

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem().setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(projectDescription)
                .clickSaveButton()
                .clickConfigure()
                .clearDescription()
                .setDescription("Description of " + FREESTYLE_PROJECT_NAME)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, "Description of " + FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testEditProjectDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickEditDescription()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickEditDescription()
                .clearDescription()
                .setDescription(EDITED_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, EDITED_PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditProjectDescription")
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
    public void testDisableProject() {
        String disabledStatus = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisableProjectButton()
                .getDesabledMassageText();

        Assert.assertTrue(disabledStatus.contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        String disableButtonText = new HomePage(getDriver())
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEnableButton()
                .getDisableProjectButtonText();

        Assert.assertEquals(disableButtonText, "Disable Project");
    }

    @Test
    public void testRenameProjectSidebarMenu() {
        List<String> actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickRename()
                .setNewName(FREESTYLE_PROJECT_NAME)
                .clickRename()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(actualResult.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testRenameProjectUsingDropdown() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameInDropdown()
                .setNewName(RENAMED_FREESTYLE_PROJECT_NAME)
                .clickRename()
                .getProjectName();

        Assert.assertEquals(projectName, RENAMED_FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectUsingBreadcrumbsDropdown() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
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
    public void testRenameWithEmptyName() {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickRenameInDropdown()
                .clearNameAndClickRenameButton();

        Assert.assertTrue(new ConfirmRenamePage(getDriver()).isErrorMessageDisplayed());
    }

    @Test
    public void testMoveToFolderInDropdown() {
        final String folderName = "Classic Models";
        final String projectName = "Race Cars";
        final String expectedResult = "Full project name: " + folderName + "/" + projectName;

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(projectName)
                .clickMoveInDropdown()
                .chooseFolderAndSave(folderName)
                .checkFullProjectName();

        Assert.assertTrue(actualResult.contains(expectedResult));
    }

    @Test
    public void testBuildNowFreestyleProject() {
        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickJobByName(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitBuildToFinish()
                .waitBuildToFinish()
                .getBuildInfo();

        Assert.assertEquals(actualResult, "#1");
    }

    @Test
    public void testDeleteFreestyleProjectFromConfigurationPage() {
        String deleteResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(deleteResult, "Welcome to Jenkins!");

    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {

        FreestyleProjectPage page = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(page.getProjectName(), FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(page.getProjectDescriptionText(), FREESTYLE_PROJECT_DESCRIPTION);

        List<String> itemList = page
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, FREESTYLE_PROJECT_NAME);

        String actualText = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickMove()
                .choosePath(FOLDER_NAME)
                .clickMoveButton()
                .getFullProjectPath();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");
    }

    @Ignore
    @Test(dependsOnMethods = "testAddProject")
    public void testOpenConfigurePageOfProject() {
        String headerText = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickConfigure()
                .getHeaderSidePanelText();

        Assert.assertEquals(
                headerText,
                "Configure",
                "Configure page of the project is not opened");
    }

    @Ignore
    @Test(dependsOnMethods = "testDescriptionAddedByUsingAddDescriptionButton")
    public void testProjectMovedToFolder() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickLogo()
                .openItemDropdown(FREESTYLE_PROJECT_NAME)
                .clickMoveInDropdown()
                .chooseFolderAndSave(FOLDER_NAME)
                .clickSaveButton()
                .clickLogo()
                .clickFolderName();

        Assert.assertTrue(folderProjectPage.getProjectName().contains(FOLDER_NAME));
        Assert.assertTrue(folderProjectPage.isItemExistsInsideFolder(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void deleteFreestyleProject() {
        List<WebElement> projectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getTheListOfFreestyleProjects(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(projectList.isEmpty());
    }

    @Test
    public void testRenameProjectToSameName() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickRename()
                .setNewName(FREESTYLE_PROJECT_NAME)
                .clickRenameAnyway()
                .isErrorMessageDisplayed());
    }

    @Ignore
    @Test(dependsOnMethods = "testRenameSideBarMenu")
    public void testDeleteProjectViaDropdown() {

        boolean ItemExists = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .openItemDropdown()
                .clickDropdownDeleteProject()
                .clickYesForDeleteFolder()
                .isItemExists(FREESTYLE_PROJECT_NAME);

        Assert.assertFalse(ItemExists);
    }

    @Test
    public void testDeleteProjectViaSideBar() {
        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedItemName()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }


    @Test
    public void testCreateNewFreestyleProjectWithDescription() {

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(FREESTYLE_PROJECT_DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(freestyleProjectPage.getProjectDescriptionText(), FREESTYLE_PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDescription")
    public void testCheckNewFreestyleProjectDescription() {

        String description = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .getProjectDescriptionText();

        Assert.assertEquals(description, FREESTYLE_PROJECT_DESCRIPTION);
    }

    @Ignore
    @Test(dependsOnMethods = "testFreestyleProjectMoveToFolder")
    public void testCheckFreestyleProjectViaBreadcrumb() {

        List<String> itemListInsideFolder = new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .clickMyViewsFromDropdown()
                .clickBreadcrumbAll()
                .clickJobNameBreadcrumb(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Ignore
    @Test
    public void testCreatExistingFreestyleProject() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .isErrorItemNameInvalidDisplayed());
    }

    @Test
    public void testDeleteProject() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .clickLogo()
                .getItemList()
                .isEmpty());
    }

    @Ignore
    @Test(dependsOnMethods = "testCreate")
    public void testDeleteUsingDropdown() {
        String projectName = "Freestyle-" + UUID.randomUUID();
        int beforeSize = new HomePage(getDriver()).getItemList().size();

        new HomePage(getDriver())
                .openItemDropdown(projectName)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()));

        Assert.assertEquals(beforeSize - 1, new HomePage(getDriver()).getItemList().size());
    }

    @Ignore
    @Test(dependsOnMethods = "testFreestyleProjectCreate")
    public void testErrorMessage() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Boolean isEnabled = new CreateNewItemPage(getDriver()).isOkButtonEnabled();

        getDriver().findElement(By.id("name")).sendKeys(Keys.ENTER);

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘FreeStyleFirst’");
        Assert.assertFalse(isEnabled);
    }

    @Ignore
    @Test(dependsOnMethods = "testErrorMessage")
    public void testDelete() {

        boolean isItemDeleted = new HomePage(getDriver())
                .clickDeleteItemAndConfirm(FREESTYLE_PROJECT_NAME)
                .isItemDeleted(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(isItemDeleted);
    }
}
