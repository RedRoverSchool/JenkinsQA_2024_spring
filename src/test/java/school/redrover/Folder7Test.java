package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder7Test extends BaseTest {

    final String OLD_NAME = "Random Folder";
    final String NEW_NAME = "Renamed Folder";

    @Ignore
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
    public void testCreateFolderUsingName() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(OLD_NAME);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test(dependsOnMethods = "testCreateFolderUsingName")
    public void testRenameFolder() {

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

    @Test(dependsOnMethods = "testCreateFolderUsingName")
    public void testDeleteFolderViaDropdown() {

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        actions
                .moveToElement(getDriver().findElement(By.xpath(
                        "//tr//button[@class='jenkins-menu-dropdown-chevron']"))).click()
                .pause(1000)
                .perform();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tippy-5']//button")))
                .click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//h1[text()='Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");
    }
}