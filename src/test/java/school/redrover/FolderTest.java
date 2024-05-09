package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderStatusPage;
import school.redrover.model.HomePage;
import school.redrover.model.PipelinePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "First_Folder";
    private static final String NEW_FOLDER_NAME = "Renamed_First_Folder";
    private static final String THIRD_FOLDER_NAME = "Dependant_Test_Folder";
    private static final String FOLDER_TO_MOVE = "Folder_to_move_into_the_first";
    private static final String PIPELINE_NAME = "Pipeline Sv";

    private void clickOnDropdownArrow(By locator) {
        WebElement itemDropdownArrow = getDriver().findElement(locator);

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", itemDropdownArrow);
    }

    public void create() {
        HomePage homePage = new HomePage(getDriver());

        homePage.clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();
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

    @Test
    public void testCreateFolderViaCreateAJob() {
        String folderBreadcrumbName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbName();

        Assert.assertEquals(folderBreadcrumbName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateFolderViaCreateAJob")
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

    @Test(dependsOnMethods = {"testCreateFolderViaCreateAJob", "testRenameFolderViaFolderBreadcrumbsDropdownMenu"})
    public void testRenameFolderViaMainPageDropdownMenu() {
        WebElement dashboardFolderName = getDriver().findElement(By.cssSelector("td>[href^='job']"));
        new Actions(getDriver())
                .moveToElement(dashboardFolderName)
                .perform();

        clickOnDropdownArrow(By.cssSelector("[href^='job'] [class$='dropdown-chevron']"));
        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(THIRD_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        String folderPageHeading = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(folderPageHeading, THIRD_FOLDER_NAME,
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
                .chooseDestinationFromListAndSave(FOLDER_NAME)
                .clickMainFolderName(FOLDER_NAME)
                .getNestedFolderName();

        Assert.assertEquals(nestedFolder, FOLDER_TO_MOVE, FOLDER_TO_MOVE + " is not in " + FOLDER_NAME);
    }

    @Test
    public void testRename() {
        create();

        HomePage homePage = new HomePage(getDriver());

        String resultName = homePage
                .clickOnCreatedFolder(FOLDER_NAME)
                .clickOnRenameButton()
                .setNewName(NEW_FOLDER_NAME)
                .clickRename()
                .getBreadcrumbName();

        Assert.assertEquals(resultName, NEW_FOLDER_NAME);
    }

    @Test
    public void testCreateViaNewItem() {
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton();
        String folderName = folderStatusPage.getBreadcrumbName();

        Assert.assertEquals(folderName, FOLDER_NAME);

        List<String> itemList = folderStatusPage
                .clickLogo()
                .getItemList();

        Assert.assertTrue((itemList.contains(FOLDER_NAME)));

    }

    @Test
    public void testCreateJobPipelineInFolder() {
        String expectedText = String.format("Full project name: %s/%s", FOLDER_NAME, PIPELINE_NAME);

        create();

        PipelinePage pipelinePage = new HomePage(getDriver())
                .clickFolderName()
                .clickNewItemInsideFolder()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton();

        String actualText = pipelinePage.getFullProjectNameLocationText();

        Assert.assertTrue(actualText.contains(expectedText), "The text does not contain the expected project name.");

        String itemName = pipelinePage.clickLogo()
                .clickFolderName()
                .getItemInTableName();

        Assert.assertEquals(itemName, PIPELINE_NAME);

    }


}
