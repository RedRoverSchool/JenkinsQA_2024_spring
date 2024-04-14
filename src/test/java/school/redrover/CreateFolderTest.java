package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFolderTest extends BaseTest {
    private static final String FIRST_FOLDER_NAME = "First Folder";
    private static final String INNER_FOLDER_NAME = "Inner Folder";
    private static final String ANOTHER_FOLDER_NAME = "Another Inner Folder";

    @Test
    public void testCreateFolder() {
        createFolder(FIRST_FOLDER_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), FIRST_FOLDER_NAME);
        Assert.assertTrue(getLinkToFolder(FIRST_FOLDER_NAME).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.className("empty-state-section")).isDisplayed());

        createFolder(INNER_FOLDER_NAME);
        getLinkToFolder(FIRST_FOLDER_NAME).click();
        createFolder(ANOTHER_FOLDER_NAME);
        getLinkToFolder(FIRST_FOLDER_NAME).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), FIRST_FOLDER_NAME);
        Assert.assertTrue(getLinkToFolder(INNER_FOLDER_NAME).isDisplayed());
        Assert.assertTrue(getLinkToFolder(ANOTHER_FOLDER_NAME).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//a[.='New Item']")).isDisplayed());
    }

    private void createFolder(String name) {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    private WebElement getLinkToFolder(String folderName) {
        return getDriver().findElement(By.xpath("//a[.='" + folderName + "']"));
    }
}
