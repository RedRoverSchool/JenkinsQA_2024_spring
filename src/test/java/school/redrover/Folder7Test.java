package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class Folder7Test extends BaseTest {
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

        createNewItemAndReturnToDashboard(this, name , Item.FOLDER);
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description")).sendKeys(newText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(newText));
    }
}