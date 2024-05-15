package school.redrover;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DeleteDialog;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;

import java.util.List;


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
    public void testCreateViaCreateAJob() {
        String folderBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        Assert.assertEquals(folderBreadcrumbName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateViaCreateAJob")
    public void testAddDescription() {
        String textInDescription = new FolderProjectPage(getDriver())
                .clickAddOrEditDescription()
                .setDescription(FOLDER_DESCRIPTION_FIRST)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_FIRST);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testChangeDescription() {
        String textInDescription = new FolderProjectPage(getDriver())
                .clickAddOrEditDescription()
                .clearDescription()
                .setDescription(FOLDER_DESCRIPTION_SECOND)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textInDescription, FOLDER_DESCRIPTION_SECOND);
    }

    @Test
    public void testDotAsFirstFolderNameCharErrorMessage() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName(".")
                .getErrorMessageInvalidCharacter();

        Assert.assertEquals(errorMessageText, "» “.” is not an allowed name",
                "The error message is different");
    }

    @Test
    public void testDotAsLastFolderNameCharErrorMessage() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName("Folder." + Keys.TAB)
                .getErrorMessageInvalidCharacter();

        Assert.assertEquals(errorMessageText, "» A name cannot end with ‘.’",
                "The error message is different");
    }

    @Test(dependsOnMethods = "testCreateViaCreateAJob")
    public void testRenameFolderViaFolderBreadcrumbsDropdownMenu() {
        String folderStatusPageHeading = new HomePage(getDriver())
                .clickSpecificFolderName(FOLDER_NAME)
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownRenameButton()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename()
                .getPageHeading();

        Assert.assertEquals(folderStatusPageHeading, NEW_FOLDER_NAME,
                "The Folder name is not equal to " + NEW_FOLDER_NAME);
    }

    @Test(dependsOnMethods = {"testCreateViaCreateAJob", "testRenameFolderViaFolderBreadcrumbsDropdownMenu"})
    public void testRenameFolderViaMainPageDropdownMenu() {
        String folderStatusPageHeading = new HomePage(getDriver())
                .openItemDropdownWithSelenium(NEW_FOLDER_NAME)
                .renameFolderFromDropdown()
                .setNewName(THIRD_FOLDER_NAME)
                .clickRename()
                .getPageHeading();

        Assert.assertEquals(folderStatusPageHeading, THIRD_FOLDER_NAME,
                "The Folder name is not equal to " + THIRD_FOLDER_NAME);
    }

    @Test
    public void testRenameFolderViaSidebarMenu() {
        String folderRenamedName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickOnRenameButton()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename()
                .getPageHeading();

        Assert.assertEquals(folderRenamedName, NEW_FOLDER_NAME);
    }

    @Test
    public void testFolderMovedIntoAnotherFolderViaBreadcrumbs() {
        String nestedFolder = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName(FOLDER_TO_MOVE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownMoveButton()
                .chooseDestinationFromListAndMove(FOLDER_NAME)
                .clickMainFolderName(FOLDER_NAME)
                .getNestedFolderName();

        Assert.assertEquals(nestedFolder, FOLDER_TO_MOVE, FOLDER_TO_MOVE + " is not in " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testFolderMovedIntoAnotherFolderViaBreadcrumbs")
    public void testCreateMultiConfigurationProjectInFolder(){
        final String MULTI_CONFIGURATION_NAME = "MultiConfigurationProject_1";

        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(MULTI_CONFIGURATION_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickFolder(FOLDER_NAME);

        Assert.assertTrue(folderProjectPage.isItemExistsInsideFolder(MULTI_CONFIGURATION_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectInFolder")
    public void testDeleteFolderViaDropdown() {

        boolean isFolderDeleted = new FolderProjectPage(getDriver())
                .clickLogo()
                .openItemDropdown(FOLDER_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(FOLDER_NAME);

        Assert.assertTrue(isFolderDeleted);
    }

    @Test
    public void testMoveFolderToFolderViaChevron() {
        List<String> folderNameList = new HomePage(getDriver())
                .createNewFolder(FOLDER_TO_MOVE)
                .createNewFolder(FOLDER_NAME)
                .openItemDropdown(FOLDER_TO_MOVE)
                .chooseFolderToMove()
                .chooseDestinationFromListAndMove(FOLDER_NAME)
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertEquals(folderNameList.get(0), FOLDER_TO_MOVE);
    }

    @Test
    public void testCreateViaNewItem() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton();
        String folderName = folderProjectPage.getBreadcrumbName();

        List<String> itemList = folderProjectPage
                .clickLogo()
                .getItemList();

        Assert.assertEquals(folderName, FOLDER_NAME);
        Assert.assertTrue((itemList.contains(FOLDER_NAME)));
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testCheckNewFolderIsEmpty() {
        Boolean isFolderEmpty = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .isFolderEmpty();

        Assert.assertTrue(isFolderEmpty);
    }

    @Test(dependsOnMethods = "testCheckNewFolderIsEmpty")
    public void testNewlyCreatedFolderIsEmptyAJ() {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        String actualFolderName = new HomePage(getDriver())
                .createNewFolder(folderName)
                .clickFolder(folderName)
                .getPageHeading();

        String actualEmptyStateMessage = new FolderProjectPage(getDriver())
                .getMessageFromEmptyFolder();

        String actualCreateJobLinkText = new FolderProjectPage(getDriver())
                .getTextWhereClickForCreateJob();

        Boolean isLinkForCreateJobDisplayed = new FolderProjectPage(getDriver())
                .isLinkForCreateJobDisplayed();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualCreateJobLinkText, createAJobLinkText);
        Assert.assertTrue(isLinkForCreateJobDisplayed, "newJobLink is NOT displayed");
    }

    @Test(dependsOnMethods = "testNewlyCreatedFolderIsEmptyAJ")
    public void testCreateJobPipelineInFolder() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, PIPELINE_NAME);

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton();

        String actualText = pipelineProjectPage.getFullProjectNameLocationText();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

        String itemName = pipelineProjectPage.clickLogo()
                .clickFolderName()
                .getItemInTableName();

        Assert.assertEquals(itemName, PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreateJobPipelineInFolder")
    public void testCreateTwoInnerFolder() {
        List<String> itemNames = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(FOLDER_TO_MOVE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(FOLDER_TO_MOVE_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(itemNames.contains(FOLDER_TO_MOVE) && itemNames.contains(FOLDER_TO_MOVE_2));
    }

    @Test(dependsOnMethods = "testCreateTwoInnerFolder")
    public void testCreateFreeStyleProjectInsideRootFolder() {
        List<String> insideFilderItemList = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(IVAN_S_FREE_STYLE_PROJECT)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertListContainsObject(insideFilderItemList, IVAN_S_FREE_STYLE_PROJECT, "FreeStyle Project was not created");
    }

    @Test(dependsOnMethods = "testCreateFreeStyleProjectInsideRootFolder")
    public void testDeleteFolder() {
        List<String> jobList =new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickDeleteOnSidebar()
                .clickYesForDeleteFolder()
                .getItemList();

        Assert.assertListNotContainsObject(jobList, FOLDER_NAME, FOLDER_NAME + " not removed!");
    }
}
