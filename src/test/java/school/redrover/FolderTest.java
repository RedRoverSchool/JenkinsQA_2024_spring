package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderTest extends BaseTest {

    private static final By NAME_ERROR_MESSAGE_LOCATOR = By.id("itemname-invalid");

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
        getDriver().findElement(By.id("name")).sendKeys("Folder.");

        String dotAsLastCharErrorMessage = getDriver().findElement(NAME_ERROR_MESSAGE_LOCATOR).getText();
        Assert.assertEquals(dotAsLastCharErrorMessage, "» A name cannot end with ‘.’",
                "The error message is different");
    }
}
