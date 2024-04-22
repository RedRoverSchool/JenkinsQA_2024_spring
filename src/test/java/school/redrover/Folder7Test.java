package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.createNewItemAndReturnToDashboard;

public class Folder7Test extends BaseTest {

    final String OLD_NAME = "Random Folder";
    final String NEW_NAME = "Renamed Folder";

    @Test
    public void testCreateNewFolder() {
        final String name = "19 April";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel h1")).getText(), name);
    }
    @Test
    public void testAddFolderDescription() {
        final String name = "19 April";
        final String newText = "Hello";

        TestUtils.createNewItemAndReturnToDashboard(this, name, TestUtils.Item.FOLDER);

        getDriver().findElement(By.cssSelector("td>a[href= 'job/19%20April/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(newText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(newText));
    }

    public void createFolderUsingName(String name) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void renameFolderNameViaDropdown() {

        createFolderUsingName(OLD_NAME);

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.linkText(OLD_NAME)).click();
        getDriver().findElement(By.linkText("Rename")).click();

        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).sendKeys(
                NEW_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.linkText(NEW_NAME)).getText(), "Renamed Folder");
    }
}