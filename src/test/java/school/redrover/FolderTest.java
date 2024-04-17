package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderTest extends BaseTest {

    private static final By NAME_ERROR_MESSAGE_LOCATOR = By.id("itemname-invalid");
    private static final String FOLDER_NAME = "First_Folder";
    private static final String NEW_FOLDER_NAME = "Renamed_First_Folder";

    private void createFolderViaCreateAJob() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDotAsFirstFolderNameCharErrorMessage() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("name")).sendKeys(".");

        String dotAsFirstCharErrorMessage = getDriver().findElement(NAME_ERROR_MESSAGE_LOCATOR).getText();
        Assert.assertEquals(dotAsFirstCharErrorMessage, "» “.” is not an allowed name",
                "The error message is different");
    }

    @Test
    public void testDotAsLastFolderNameCharErrorMessage() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder." + Keys.TAB);

        String dotAsLastCharErrorMessage = getDriver().findElement(NAME_ERROR_MESSAGE_LOCATOR).getText();
        Assert.assertEquals(dotAsLastCharErrorMessage, "» A name cannot end with ‘.’",
                "The error message is different");
    }

    @Test
    public void testCreateFolderViaCreateAJob() {
        createFolderViaCreateAJob();
        String breadcrumbFolderName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']")).getText();

        Assert.assertEquals(breadcrumbFolderName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }

    @Test
    public void testRenameFolderViaFolderBreadcrumbsDropdownMenu() {
        createFolderViaCreateAJob();

        WebElement breadcrumbFolderName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']"));
        new Actions(getDriver())
                .moveToElement(breadcrumbFolderName)
                .perform();

        WebElement breadcrumbDropdownArrow = getDriver().findElement(
                By.cssSelector("[href^='/job'] [class='jenkins-menu-dropdown-chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", breadcrumbDropdownArrow);

        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        String folderPageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(folderPageHeading, NEW_FOLDER_NAME,
                "The Folder name is not equal to " + NEW_FOLDER_NAME);
    }
}
