package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject100Test extends BaseTest {

    @Test
    public void testDeleteUsingDropdown() {
        final String projectName = "This is the project to be deleted";

        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.FREESTYLE_PROJECT);

        TestUtils.openElementDropdown(this, TestUtils.getViewItemElement(this, projectName));

        getDriver().findElement(TestUtils.DROPDOWN_DELETE).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(TestUtils.DIALOG_DEFAULT_BUTTON)).click();

        Assert.assertTrue(getDriver().findElement(TestUtils.EMPTY_STATE_BLOCK).isDisplayed());
    }

    @Test
    public void testRenameProjectUsingDropdown() {
        final String projectName = "This is the project to be renamed";
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.FREESTYLE_PROJECT);

        final String projectNewName = "Renamed project";
        TestUtils.openElementDropdown(this, TestUtils.getViewItemElement(this, projectName));
        getDriver().findElement(TestUtils.DROPDOWN_RENAME).click();

        WebElement newName = getDriver().findElement(By.name("newName"));
        newName.clear();
        newName.sendKeys(projectNewName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div#main-panel h1")).getText(),
                projectNewName);
    }

    @Test
    public void testDeleteUsingSidePanel() {
        final String projectName = "This is the project to be deleted";
        TestUtils.createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.FREESTYLE_PROJECT);

        TestUtils.clickAtBeginOfElement(this, TestUtils.getViewItemElement(this, projectName));

        getDriver().findElement(TestUtils.SIDE_PANEL_DELETE).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(TestUtils.DIALOG_DEFAULT_BUTTON)).click();

        Assert.assertTrue(getDriver().findElement(TestUtils.EMPTY_STATE_BLOCK).isDisplayed());
    }
}
