package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderTest extends BaseTest {

    private static final By NAME_ERROR_MESSAGE_LOCATOR = By.id("itemname-invalid");
    private static final String FOLDER_NAME = "First_Folder";

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
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        String breadcrumbFolderName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']")).getText();

        Assert.assertEquals(breadcrumbFolderName, FOLDER_NAME, "Breadcrumb name doesn't match " + FOLDER_NAME);
    }
}
