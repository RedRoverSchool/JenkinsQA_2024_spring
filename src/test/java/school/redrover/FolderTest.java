package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
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
    private static final String MOVED_FOLDER = "Folder_to_move_into_the_first";
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
                .typeItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        Allure.step("Expected result: Created project name matches breadcrumb name");
        Assert.assertEquals(folderBreadcrumbName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }

    @Test
    @Story("US_04.004  Add and edit description of the folder ")
    @Description("Add description of the folder and save it")
    public void testAddDescription() {
        TestUtils.createFolderProject(this, FOLDER_NAME);

        String textInDescription = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickAddDescription()
                .typeDescription(FOLDER_DESCRIPTION_FIRST)
                .clickSaveButton()
                .getDescriptionText();

        Allure.step("Expected result: Description text is displayed");
        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_FIRST);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("US_04.004  Add and edit description of the folder ")
    @Description("Edit description of the folder and save it")
    public void testEditDescription() {
        String textInDescription = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickEditDescription()
                .clearDescription()
                .typeDescription(FOLDER_DESCRIPTION_SECOND)
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
                .typeItemName(".")
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step("Expected result: The error message is displayed");
        Assert.assertEquals(errorMessageText, "» “.” is not an allowed name");
    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify error message when create a folder with a dot as the last character")
    public void testErrorWhenCreatingFolderWithDotAsLastCharacter() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .typeItemName("Folder." + Keys.TAB)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step("Expected result: The error message is displayed");
        Assert.assertEquals(errorMessageText, "» A name cannot end with ‘.’");
    }

    @Test
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via Breadcrumbs dropdown menu")
    public void testRenameFolderViaBreadcrumbs() {
        TestUtils.createFolderProject(this, FOLDER_NAME);

        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .hoverOverProjectNameOnBreadcrumbs(FOLDER_NAME)
                .clickBreadcrumbsArrowAfterProjectName(FOLDER_NAME)
                .clickRenameOnBreadcrumbsMenu()
                .clearNameInputField()
                .typeNewName(NEW_FOLDER_NAME)
                .clickRenameButtonWhenRenamedViaBreadcrumbs();

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

    @Test(dependsOnMethods = "testRenameFolderViaBreadcrumbs")
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via main page dropdown")
    public void testRenameFolderViaMainPageDropdownMenu() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .openItemDropdown(NEW_FOLDER_NAME)
                .clickRenameOnDropdown()
                .clearNameInputField()
                .typeNewName(THIRD_FOLDER_NAME)
                .clickRenameButtonWhenRenamedViaDropdown(new FolderProjectPage(getDriver()));

        String folderStatusPageHeading = folderProjectPage
                .getProjectName();

        HomePage renewHomePage = folderProjectPage
                .clickLogo();

        Allure.step("Expected result: the renamed Project is displayed");
        Assert.assertEquals(folderStatusPageHeading, THIRD_FOLDER_NAME);
        Assert.assertTrue(renewHomePage.isItemExists(THIRD_FOLDER_NAME));
        Assert.assertTrue(renewHomePage.isItemDeleted(NEW_FOLDER_NAME));
    }

    @Test
    @Story("US_04.001 Rename Folder")
    @Description("Rename Folder via sidebar")
    public void testRenameFolderViaSidebarMenu() {
        TestUtils.createFolderProject(this, FOLDER_NAME);

        String folderRenamedName = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickRenameOnSidebar()
                .clearNameInputField()
                .typeNewName(NEW_FOLDER_NAME)
                .clickRenameButtonWhenRenamedViaSidebar()
                .getProjectName();

        HomePage renewHomePage = new  FolderProjectPage(getDriver())
                .clickLogo();

        Allure.step("Expected result: Folder name on project page as entered during renaming");
        Assert.assertEquals(folderRenamedName, NEW_FOLDER_NAME);

        Allure.step("Expected result: Folder with renamed name is exists on dashboard");
        Assert.assertTrue(renewHomePage.isItemExists(NEW_FOLDER_NAME));

        Allure.step("Expected result: Folder with old name is missing on dashboard");
        Assert.assertTrue(renewHomePage.isItemDeleted(FOLDER_NAME));
    }

    @Test
    @Story("US_04.002  Move Folder to Folder")
    @Description("Verify a Folder can be moved into another Folder via breadcrumbs")
    public void testFolderMovedIntoAnotherFolderViaBreadcrumbs() {
        TestUtils.createFolderProject(this, FOLDER_NAME);
        TestUtils.createFolderProject(this, MOVED_FOLDER);

        String nestedFolder = new HomePage(getDriver())
                .clickSpecificFolderName(MOVED_FOLDER)
                .hoverOverProjectNameOnBreadcrumbs(MOVED_FOLDER)
                .clickBreadcrumbsArrowAfterProjectName(MOVED_FOLDER)
                .clickMoveOnBreadcrumbs()
                .selectDestinationFolderFromList(FOLDER_NAME)
                .clickMoveButtonWhenMovedViaBreadcrumbs()
                .clickFolderNameOnBreadcrumbs(FOLDER_NAME)
                .getNestedProjectName();

        Allure.step("Expected result: Nested project is in destination Folder");
        Assert.assertEquals(nestedFolder, MOVED_FOLDER, MOVED_FOLDER + " is not in " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testFolderMovedIntoAnotherFolderViaBreadcrumbs")
    @Story("US_04.005  Create a job inside folder")
    @Description("Add Multi Configuration Project inside folder")
    public void testCreateMultiConfigurationProjectInFolder() {
        final String multiConfigurationProject = "MultiConfigurationProject_1";

        boolean isItemCreated = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemOnSidebar()
                .typeItemName(multiConfigurationProject)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .isItemExistsInsideFolder(multiConfigurationProject);

        Allure.step("Expected result: Nested project is created");
        Assert.assertTrue(isItemCreated);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectInFolder")
    @Story("US_04.003 Delete Folder")
    @Description("Delete folder from dropdown menu")
    public void testDeleteFolderViaDropdown() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openItemDropdown(FOLDER_NAME)
                .clickDeleteOnDropdown()
                .clickYesForConfirmDelete()
                .isItemDeleted(FOLDER_NAME);

        Allure.step("Expected result: Project is deleted and not displayed on Home page anymore");
        Assert.assertTrue(isFolderDeleted);
    }

    @Test
    @Story("US_04.002  Move Folder to Folder")
    @Description("Verify a Folder can be moved into another Folder via dropdown menu")
    public void testMoveFolderToFolderViaDropdownMenu() {
        TestUtils.createFolderProject(this, MOVED_FOLDER);
        TestUtils.createFolderProject(this, FOLDER_NAME);

        List<String> folderNameList = new HomePage(getDriver())
                .openItemDropdown(MOVED_FOLDER)
                .clickMoveOnDropdown()
                .selectDestinationFolderFromList(FOLDER_NAME)
                .clickMoveButtonWhenMovedViaDropdown(new FolderProjectPage(getDriver()))
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Nested project is displayed in destination Folder");
        Assert.assertEquals(folderNameList.get(0), MOVED_FOLDER);    }

    @Test
    @Story("US_04.000  Create Folder")
    @Description("Verify the creation of a folder via Sidebar Menu")
    public void testCreateViaSidebarMenu() {
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        List<String> itemList = new FolderProjectPage(getDriver())
                .clickLogo()
                .getItemList();

        Allure.step("Expected result: Created Project is displayed on Breadcrumbs");
        Assert.assertEquals(folderName, FOLDER_NAME);

        Allure.step("Expected result: Created Project is displayed on Dashboard");
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
    public void testCreatedFolderIsEmpty() {
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
                .getLinkTextForCreateJob();

        Boolean isLinkForCreateJobDisplayed = new FolderProjectPage(getDriver())
                .isLinkForCreateJobDisplayed();

        Allure.step("Expected result: Section 'This folder is empty' is displayed");
        Assert.assertEquals(actualFolderName, folderName);

        Allure.step("Expected result: Text 'This folder is empty' is displayed");
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);

        Allure.step("Expected result: Text for creating job link = 'Create a job'");
        Assert.assertEquals(actualCreateJobLinkText, createAJobLinkText);

        Allure.step("Expected result: Link for creating job' is displayed");
        Assert.assertTrue(isLinkForCreateJobDisplayed, "newJobLink is NOT displayed");
    }

    @Test(dependsOnMethods = "testCreatedFolderIsEmpty")
    @Story("US_04.005  Create a job inside folder")
    @Description("Add Pipeline Project inside folder")
    public void testCreateJobPipelineInFolder() {
        String expectedFullProjectName = String.format("Full project name: %s/%s", FOLDER_NAME, PIPELINE_NAME);

        String fullProjectName = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemOnSidebar()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getFullProjectNameLocationText();

        boolean isItemExistsInsideFolder = new PipelineProjectPage(getDriver())
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .isItemExistsInsideFolder(PIPELINE_NAME);

        Allure.step("Expected result: Full Project name contains both Project names");
        Assert.assertTrue(fullProjectName.contains(expectedFullProjectName),
                "The text does not contain the expected project name.");

        Assert.assertTrue(isItemExistsInsideFolder, "Job inside Folder not created");
    }

    @Test(dependsOnMethods = "testCreateJobPipelineInFolder")
    @Story("US_04.005  Create a job inside folder")
    @Description("Verify the creation of two inner folder in existed Folder")
    public void testCreateTwoInnerFolder() {
        List<String> itemNames = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemOnSidebar()
                .typeItemName(MOVED_FOLDER)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemOnSidebar()
                .typeItemName(FOLDER_TO_MOVE_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Item list inside folder contains both created projects");
        Assert.assertTrue(itemNames.contains(MOVED_FOLDER) && itemNames.contains(FOLDER_TO_MOVE_2));
    }

    @Test(dependsOnMethods = "testCreateTwoInnerFolder")
    @Story("US_04.005  Create a job inside folder")
    @Description("Verify the creation of Freestyle project in existed Folder")
    public void testCreateFreeStyleProjectInsideRootFolder() {
        List<String> insideFolderItemList = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickNewItemOnSidebar()
                .typeItemName(IVAN_S_FREE_STYLE_PROJECT)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificFolderName(FOLDER_NAME)
                .getItemListInsideFolder();

        Allure.step("Expected result: Item list inside folder contains Freestyle project");
        Assert.assertListContainsObject(insideFolderItemList, IVAN_S_FREE_STYLE_PROJECT,
                "FreeStyle Project was not created");
    }

    @Test(dependsOnMethods = "testCreateFreeStyleProjectInsideRootFolder")
    @Story("US_04.003 Delete Folder")
    @Description("Delete folder from folder's page using left menu panel")
    public void testDeleteFolder() {
        List<String> jobList = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .clickDeleteOnSidebar()
                .clickYesWhenDeletedItemOnHomePage()
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
                .typeItemName("Fold%erdate")
                .selectFolderAndClickOk()
                .getHeadingText();

        Allure.step("Expected result: The Error message is displayed");
        Assert.assertEquals(header1Text, "Error");
    }
}
