package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder7Test extends BaseTest {

    private final String NAME = "19 April";
    final String OLD_NAME = "Random Folder";
    final String NEW_NAME = "Renamed Folder";

    @Test
    public void testCreateNewFolder() {
        String name = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getPageHeading();

        Assert.assertEquals(name, NAME);
    }

    @Test
    public void testAddFolderDescription(){

        final String newText = "Hello";

        String description = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickAddOrEditDescription()
                .setDescription(newText)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, newText);
    }

    @Test
    public void testCreateFolderUsingName() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(OLD_NAME);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Ignore
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

    @Ignore
    @Test(dependsOnMethods = "testCreateFolderUsingName")
    public void testDeleteFolderViaDropdown() {

        TestUtils.goToMainPage(getDriver());

        WebElement dropdownChevron = getDriver().findElement(By.xpath(
                "//tr//button[@class='jenkins-menu-dropdown-chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);

        getDriver().findElement(By.xpath(
                "//*[@id='tippy-5']//button")).click();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//h1[text()='Welcome to Jenkins!']")).getText(), "Welcome to Jenkins!");
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
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