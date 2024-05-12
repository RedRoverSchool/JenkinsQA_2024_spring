package school.redrover;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "First_Folder";

    private static final String NEW_FOLDER_NAME = "Renamed_First_Folder";

    private static final String RENAMED_FOLDER_NAME = "Renamed Folder";

    private static final String THIRD_FOLDER_NAME = "Dependant_Test_Folder";

    private static final String FOLDER_TO_MOVE = "Folder_to_move_into_the_first";

    private static final String PIPELINE_NAME = "Pipeline Sv";

    private static final String FOLDER_DESCRIPTION_FIRST = "Some description of the folder.";
    private static final String FOLDER_DESCRIPTION_SECOND = "NEW description of the folder.";

    @Test
    public void testDotAsFirstFolderNameCharErrorMessage() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName(".")
                .getErrorMessage();

        Assert.assertEquals(errorMessageText, "» “.” is not an allowed name",
                "The error message is different");
    }

    @Test(dependsOnMethods = "testDotAsFirstFolderNameCharErrorMessage")
    public void testDotAsLastFolderNameCharErrorMessage() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName("Folder." + Keys.TAB)
                .getErrorMessage();

        Assert.assertEquals(errorMessageText, "» A name cannot end with ‘.’",
                "The error message is different");
    }

    @Test(dependsOnMethods = "testDotAsLastFolderNameCharErrorMessage")
    public void testCreateViaNewItem() {
        String pageTopic = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getPageHeading();

        String onBreadcrumbFolderName = new FolderProjectPage(getDriver())
                .getBreadcrumbName();

        List<String> onHomePageItemList = new FolderProjectPage(getDriver())
                .clickLogo()
                .getItemList();

        Assert.assertEquals(pageTopic, FOLDER_NAME);
        Assert.assertEquals(onBreadcrumbFolderName, FOLDER_NAME);
        Assert.assertListContainsObject(onHomePageItemList, FOLDER_NAME, "Folder Not Created");
    }

    @Test(dependsOnMethods = "testCreateViaNewItem")
    public void testCheckNewFolderIsEmpty() {
        Boolean isFolderEmpty = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .isFolderEmpty();

        Assert.assertTrue(isFolderEmpty);
    }

    @Test(dependsOnMethods = "testCheckNewFolderIsEmpty")
    public void testCreateTwoInnerFolder() {
        List<String> itemNames = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(NEW_FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(THIRD_FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickFolder(FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(itemNames.contains(NEW_FOLDER_NAME)
                && itemNames.contains(THIRD_FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testCreateTwoInnerFolder")
    public void testRename() {
        String resultName = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickOnRenameButton()
                .setNewName(RENAMED_FOLDER_NAME)
                .clickRename()
                .getBreadcrumbName();

        Assert.assertEquals(resultName, RENAMED_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testRename")
    public void testMoveFolderToFolderViaChevron() {
        List<String> folderNameList = new HomePage(getDriver())
                .createNewFolder(FOLDER_TO_MOVE)
                .openItemDropdown(FOLDER_TO_MOVE)
                .chooseFolderToMove()
                .chooseDestinationFromListAndMove(RENAMED_FOLDER_NAME)
                .clickLogo()
                .clickFolder(RENAMED_FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertListContainsObject(folderNameList, FOLDER_TO_MOVE, "Folder Not Moved");
    }

    @Test(dependsOnMethods = "testMoveFolderToFolderViaChevron")
    public void testCreateJobPipelineInFolder() {
        final String expectedText = String.format("Full project name: %s/%s", RENAMED_FOLDER_NAME, PIPELINE_NAME);
        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickFolder(RENAMED_FOLDER_NAME)
                .clickNewItemInsideFolder()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton();

        String actualText = pipelineProjectPage
                .getFullProjectNameLocationText();

        List<String> insideFolderItemList = pipelineProjectPage
                .clickLogo()
                .clickFolder(RENAMED_FOLDER_NAME)
                .getItemListInsideFolder();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");
        Assert.assertListContainsObject(insideFolderItemList, PIPELINE_NAME, "Item Not Found:" + PIPELINE_NAME);
    }

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
    @Test(dependsOnMethods = {"testCreateViaCreateAJob", "testAddDescription"})
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
                .getErrorMessage();

        Assert.assertEquals(errorMessageText, "» “.” is not an allowed name",
                "The error message is different");
    }

    @Test
    public void testDotAsLastFolderNameCharErrorMessage() {
        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .selectFolder()
                .setItemName("Folder." + Keys.TAB)
                .getErrorMessage();

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

    @Test(dependsOnMethods = "testRenameFolderViaFolderBreadcrumbsDropdownMenu")
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

    @Test(dependsOnMethods = "testRenameFolderViaMainPageDropdownMenu")
    public void testRenameFolderViaSidebarMenu() {
        String folderRenamedName = new HomePage(getDriver())
                .clickFolder(THIRD_FOLDER_NAME)
                .clickOnRenameButton()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename()
                .getPageHeading();

        Assert.assertEquals(folderRenamedName, NEW_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testRenameFolderViaSidebarMenu")
    public void testFolderMovedIntoAnotherFolderViaBreadcrumbs() {
        String nestedFolder = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_TO_MOVE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownMoveButton()
                .chooseDestinationFromListAndMove(NEW_FOLDER_NAME)
                .clickMainFolderName(NEW_FOLDER_NAME)
                .getNestedFolderName();

        Assert.assertEquals(nestedFolder, FOLDER_TO_MOVE, FOLDER_TO_MOVE + " is not in " + FOLDER_NAME);
    }

}
