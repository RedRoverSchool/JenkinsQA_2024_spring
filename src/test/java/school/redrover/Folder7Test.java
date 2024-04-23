package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.createNewItemAndReturnToDashboard;

public class Folder7Test extends BaseTest {

    private final String NAME = "19 April";
    final String OLD_NAME = "Random Folder";
    final String NEW_NAME = "Renamed Folder";

    @Test
    public void testCreateNewFolder() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel h1")).getText(), NAME);
    }
    @Test
    public void testAddFolderDescription() {

        final String newText = "Hello";

        TestUtils.createNewItemAndReturnToDashboard(this, NAME, TestUtils.Item.FOLDER);

        getDriver().findElement(By.cssSelector("td>a[href= 'job/19%20April/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(newText);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(newText));
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
        getDriver().findElement(By.xpath("//*[span='" + OLD_NAME + "']")).click();
        getDriver().findElement(By.linkText("Rename")).click();

        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).clear();
        getDriver().findElement(By.xpath("//*[@class='setting-main']/input")).sendKeys(
                NEW_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.linkText(NEW_NAME)).getText(), "Renamed Folder");
    }

    @Test(dependsOnMethods = "testRenameFolder")
    public void testDeleteFolderViaDropdown() {

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement dropdownChevron = getDriver().findElement(By.xpath(
                "//tr//button[@class='jenkins-menu-dropdown-chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@id='tippy-5']//button"))).click();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//h1[text()='Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");
    }

    @Test (dependsOnMethods = "testCreateNewFolder")
    public void testCreateFreestyleProjectInsideFolder() {
        final String freeStyleProjectName = "23 april";

        TestUtils.clickAtBeginOfElement(this, TestUtils.getViewItemElement(this, NAME));

        getDriver().findElement(By.cssSelector("[href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(freeStyleProjectName);
        getDriver().findElement(By.className(TestUtils.Item.FREESTYLE_PROJECT)).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("main-panel")))
              .getText().contains(NAME + "/" + freeStyleProjectName));
    }
}