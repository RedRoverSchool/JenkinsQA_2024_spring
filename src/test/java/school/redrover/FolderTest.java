package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DeleteDialog;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("Folder")
public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "First_Folder";
    private static final String NEW_FOLDER_NAME = "Renamed_First_Folder";
    private static final String THIRD_FOLDER_NAME = "Dependant_Test_Folder";
    private static final String FOLDER_TO_MOVE = "Folder_to_move_into_the_first";
    private static final String FOLDER_TO_MOVE_2 = "Folder_to_move_into_the_first_2";
    private static final String PIPELINE_NAME = "Pipeline Sv";
    private static final String IVAN_S_FREE_STYLE_PROJECT = "Ivan's Freestyle";
    private static final String FOLDER_DESCRIPTION_FIRST = "Some description of the folder.";
    private static final String FOLDER_DESCRIPTION_SECOND = "NEW description of the folder.";


    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify the creation of a folder via Create a job")
    public void testCreateViaCreateAJob() {
        String folderBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        Allure.step("Expected result: Created project name matches breadcrumb name");
        Assert.assertEquals(folderBreadcrumbName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateViaCreateAJob")
    @Story("US_04.004  Add and edit description of the folder ")
    @Description("Add description of the folder and save it")
    public void testAddDescription() {
        String textInDescription = new FolderProjectPage(getDriver())
                .clickAddDescription()
                .setDescription(FOLDER_DESCRIPTION_FIRST)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("Expected result: Description text is displayed");
        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_FIRST);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("US_04.004  Add and edit description of the folder ")
    @Description("Edit description of the folder and save it")
    public void testChangeDescription() {
        String textInDescription = new FolderProjectPage(getDriver())
                .clickEditDescription()
                .clearDescription()
                .setDescription(FOLDER_DESCRIPTION_SECOND)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("Expected result: New description text is displayed");
        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_SECOND);
    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify error message when create a folder with a dot as the first character")
    public void testErrorWhenCreatingFolderWithDotAsFirstCharacter() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName(".")
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step("Expected result: The error message is displayed");
        Assert.assertEquals(errorMessageText, "» “.” is not an allowed name",
                "The error message is different");
    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify error message when create a folder with a dot as the last character")
    public void testErrorWhenCreatingFolderWithDotAsLastCharacter() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName("Folder." + Keys.TAB)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step("Expected result: The error message is displayed");
        Assert.assertEquals(errorMessageText, "» A name cannot end with ‘.’",
                "The error message is different");
    }

    @Test(dependsOnMethods = "testCreateViaCreateAJob")
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via Breadcrumbs dropdown menu")
    public void testRenameFolderViaFolderBreadcrumbsDropdownMenu() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownRenameButton()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename();
        String folderStatusPageHeading = folderProjectPage
                .getProjectName();
        HomePage renewHomePage = folderProjectPage
                .clickLogo();

        Allure.step("Expected result: the renamed Project is displayed");
        Assert.assertEquals(folderStatusPageHeading, NEW_FOLDER_NAME,
                "The Folder name is not equal to " + NEW_FOLDER_NAME);
        Assert.assertTrue(renewHomePage.isItemExists(NEW_FOLDER_NAME));
        Assert.assertTrue(renewHomePage.isItemDeleted(FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"testCreateViaCreateAJob", "testRenameFolderViaFolderBreadcrumbsDropdownMenu"})
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via main page dropdown")
    public void testRenameFolderViaMainPageDropdownMenu() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .openItemDropdownWithSelenium(NEW_FOLDER_NAME)
                .clickRenameOnDropdownForFolder()
                .setNewName(THIRD_FOLDER_NAME)
                .clickRename();
        String folderStatusPageHeading = folderProjectPage
                .getProjectName();
        HomePage renewHomePage = folderProjectPage
                .clickLogo();

        Allure.step("Expected result: the renamed Project is displayed");
        Assert.assertEquals(folderStatusPageHeading, THIRD_FOLDER_NAME,
                "The Folder name is not equal to " + THIRD_FOLDER_NAME);
        Assert.assertTrue(renewHomePage.isItemExists(THIRD_FOLDER_NAME));
        Assert.assertTrue(renewHomePage.isItemDeleted(NEW_FOLDER_NAME));
    }

    @Test
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via sidebar")
    public void testRenameFolderViaSidebarMenu() {
        TestUtils.createFolderProject(this, FOLDER_NAME);

        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .clickSidebarRename()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename();
        String folderRenamedName = folderProjectPage
                .getProjectName();
        HomePage renewHomePage = folderProjectPage
                .clickLogo();

        Allure.step("Expected result: the renamed Project is displayed");
        Assert.assertEquals(folderRenamedName, NEW_FOLDER_NAME);
        Assert.assertTrue(renewHomePage.isItemExists(NEW_FOLDER_NAME));
        Assert.assertTrue(renewHomePage.isItemDeleted(FOLDER_NAME));
    }

    @Test
    @Story("US_04.002  Move Folder to Folder")
    @Description("Verify a Folder can be moved into another Folder via breadcrumbs")
    public void testFolderMovedIntoAnotherFolderViaBreadcrumbs() {
        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createFolderProject(this, FOLDER_TO_MOVE);

        String nestedFolder = new HomePage(getDriver())
                .clickJobByName(FOLDER_TO_MOVE, new FolderProjectPage(getDriver()))
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownMoveButton()
                .chooseDestinationFromListAndMove(FOLDER_NAME)
                .clickMainFolderName(FOLDER_NAME)
                .getNestedProjectName();

        Allure.step("Expected result: Nested project is displayed");
        Assert.assertEquals(nestedFolder, FOLDER_TO_MOVE, FOLDER_TO_MOVE + " is not in " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testFolderMovedIntoAnotherFolderViaBreadcrumbs")
    @Story("US_04.005  Create a job inside folder")
    @Description("Add Multi Configuration Project inside folder")
    public void testCreateMultiConfigurationProjectInFolder() {
        final String multiConfigurationProject = "MultiConfigurationProject_1";

        boolean isItemCreated = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(multiConfigurationProject)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .isItemExistsInsideFolder(multiConfigurationProject);

        Allure.step("Expected result: Nested project is displayed");
        Assert.assertTrue(isItemCreated);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectInFolder")
    @Story("US_04.003 Delete Folder")
    @Description("Delete folder from dropdown menu")
    public void testDeleteFolderViaDropdown() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openItemDropdown(FOLDER_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(FOLDER_NAME);

        Allure.step("Expected result: Project is not displayed on Home page anymore");
        Assert.assertTrue(isFolderDeleted);
    }

    @Test
    @Story("US_04.002  Move Folder to Folder")
    @Description("Verify a Folder can be moved into another Folder via dropdown menu")
    public void testMoveFolderToFolderViaDropdownMenu() {
        TestUtils.createFolderProject(this, FOLDER_TO_MOVE);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        List<String> folderNameList = new HomePage(getDriver())
                .openItemDropdown(FOLDER_TO_MOVE)
                .chooseFolderToMove()
                .chooseDestinationFromListAndMove(FOLDER_NAME)
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Nested project is displayed");
        Assert.assertEquals(folderNameList.get(0), FOLDER_TO_MOVE);
    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify the creation of a folder via Sidebar Menu")
    public void testCreateViaSidebarMenu() {
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        List<String> itemList = new FolderProjectPage(getDriver())
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Project is displayed on Breadcrumbs");
        Assert.assertEquals(folderName, FOLDER_NAME);
        Assert.assertListContainsObject(itemList, FOLDER_NAME, "Folder is not created");
    }

    @Test(dependsOnMethods = "testCreateViaSidebarMenu")
    @Story("US_04.000  Create Folder")
    @Description("Verify that created Folder is empty")
    public void testCheckNewFolderIsEmpty() {
        Boolean isFolderEmpty = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .isFolderEmpty();

        Allure.step("Expected result: Section 'This folder is empty' is displayed");
        Assert.assertTrue(isFolderEmpty);
    }

    @Test(dependsOnMethods = "testCheckNewFolderIsEmpty")
    @Story("US_04.000  Create Folder")
    @Description("Validate that a newly created folder is empty")
    public void testNewlyCreatedFolderIsEmptyAJ() {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        TestUtils.createFolderProject(this, folderName);

        String actualFolderName = new HomePage(getDriver())
                .clickSpecificFolderName(folderName)
                .getProjectName();

        String actualEmptyStateMessage = new FolderProjectPage(getDriver())
                .getMessageFromEmptyFolder();

        String actualCreateJobLinkText = new FolderProjectPage(getDriver())
                .getTextWhereClickForCreateJob();

        Boolean isLinkForCreateJobDisplayed = new FolderProjectPage(getDriver())
                .isLinkForCreateJobDisplayed();

        Allure.step("Expected result: Section 'This folder is empty' is displayed");
        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualCreateJobLinkText, createAJobLinkText);
        Assert.assertTrue(isLinkForCreateJobDisplayed, "newJobLink is NOT displayed");
    }

    @Test(dependsOnMethods = "testNewlyCreatedFolderIsEmptyAJ")
    @Story("US_04.005  Create a job inside folder")
    @Description("Add Pipeline Project inside folder")
    public void testCreateJobPipelineInFolder() {
        String expectedFullProjectName = String.format("Full project name: %s/%s", FOLDER_NAME, PIPELINE_NAME);

        String fullProjectName = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getFullProjectNameLocationText();

        String itemName = new PipelineProjectPage(getDriver())
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemInTableName();

        Allure.step("Expected result: Full Project name contains both Project names");
        Assert.assertTrue(fullProjectName.contains(expectedFullProjectName), "The text does not contain the expected project name.");
        Assert.assertEquals(itemName, PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreateJobPipelineInFolder")
    @Story("US_04.005  Create a job inside folder")
    @Description("Verify the creation of two inner folder in existed Folder")
    public void testCreateTwoInnerFolder() {
        List<String> itemNames = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(FOLDER_TO_MOVE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(FOLDER_TO_MOVE_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Item list inside folder contains both created projects");
        Assert.assertTrue(itemNames.contains(FOLDER_TO_MOVE) && itemNames.contains(FOLDER_TO_MOVE_2));
    }

    @Test(dependsOnMethods = "testCreateTwoInnerFolder")
    @Story("US_04.005  Create a job inside folder")
    @Description("Verify the creation of Freestyle project in existed Folder")
    public void testCreateFreeStyleProjectInsideRootFolder() {
        List<String> insideFolderItemList = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(IVAN_S_FREE_STYLE_PROJECT)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Item list inside folder contains Freestyle project");
        Assert.assertListContainsObject(insideFolderItemList, IVAN_S_FREE_STYLE_PROJECT, "FreeStyle Project was not created");
    }

    @Test(dependsOnMethods = "testCreateFreeStyleProjectInsideRootFolder")
    @Story("US_04.003 Delete Folder")
    @Description("Delete folder from folder's page using left menu panel")
    public void testDeleteFolder() {
        List<String> jobList = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickDeleteOnSidebar()
                .clickYesForDeleteFolder()
                .getItemList();

        Allure.step("Expected result: the project is not displayed on Home Page");
        Assert.assertListNotContainsObject(jobList, FOLDER_NAME, FOLDER_NAME + " not removed!");
    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify error message when create a folder with invalid character")
    public void testCreateProjectInvalidChar() {
        String header1Text = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Fold%erdate")
                .selectFolderAndClickOk()
                .getHeadingText();

        Allure.step("Expected result: The Error message is displayed");
        Assert.assertEquals(header1Text, "Error");
    }
}
