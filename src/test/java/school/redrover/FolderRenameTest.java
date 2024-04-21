package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderRenameTest extends BaseTest {
    private static final By NEW_NAME = By.xpath("//input[@name='newName']");

    private void createNewFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label[.='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Ignore
    @Test
    public void testRenameFolder() {
        final String folderName = "New folder";

        createNewFolder(folderName);

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(NEW_NAME).clear();
        getDriver().findElement(NEW_NAME).sendKeys("Newest Folder");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(.,'Newest Folder')]")).isDisplayed());
    }
}