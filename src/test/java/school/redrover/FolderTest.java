package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FolderConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "First_Folder";
    private static final String NEW_FOLDER_NAME = "Renamed_First_Folder";
    private static final String THIRD_FOLDER_NAME = "Dependant_Test_Folder";
    private static final String FOLDER_TO_MOVE = "Folder_to_move_into_the_first";
    private static final By NEW_NAME = By.name("newName");

    private void createFolderViaCreateAJob() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

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
        getDriver().findElement(By.cssSelector("td>[href^='job']")).click();

        WebElement breadcrumbFolderName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']"));
        new Actions(getDriver())
                .moveToElement(breadcrumbFolderName)
                .perform();

        clickOnDropdownArrow(By.cssSelector("[href^='/job'] [class$='dropdown-chevron']"));
        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        String folderPageHeading = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(folderPageHeading, NEW_FOLDER_NAME,
                "The Folder name is not equal to " + NEW_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateFolderViaCreateAJob")
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

    @Ignore
    @Test
    public void testRenameFolderViaSidebarMenu() {
        createFolderViaCreateAJob();

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(NEW_NAME).clear();
        getDriver().findElement(NEW_NAME).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), NEW_FOLDER_NAME);
    }

    @Test
    public void testFolderMovedIntoAnotherFolderViaBreadcrumbs() {
        createFolderViaCreateAJob();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_TO_MOVE);
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement breadcrumbsFolderName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']"));
        new Actions(getDriver())
                .moveToElement(breadcrumbsFolderName)
                .perform();
        clickOnDropdownArrow(By.cssSelector("[href^='/job'] [class$='dropdown-chevron']"));
        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='move']")).click();

        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job/" + FOLDER_NAME + "']")).click();

        String nestedFolder = getDriver().findElement(By.cssSelector("td [href*='job']:first-child")).getText();
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
    public void testRenameFolder() {

        HomePage homePage = new HomePage(getDriver());
        homePage
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectTypeAndClickOk("Folder");

        new FolderConfigPage(getDriver())
                .clickSaveButton()
                .clickOnRenameButtonLeft()
                .renameFolder(NEW_FOLDER_NAME)
                .clickLogo();

        List<String> itemList = homePage.getItemList();
        Assert.assertTrue(itemList.contains(NEW_FOLDER_NAME), "Folder is not renamed!");
    }
}
