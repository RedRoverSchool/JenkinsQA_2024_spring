package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class FreestyleProject100Test extends BaseTest {

    @Test
    public void testDeleteUsingDropdown() {
        final String projectName = "This is the project to be deleted";

        createNewItemAndReturnToDashboard(this, projectName, Item.FREESTYLE_PROJECT);

        deleteUsingDropdown(this, projectName);

        Assert.assertTrue(getDriver().findElement(EMPTY_STATE_BLOCK).isDisplayed());
    }

    @Test
    public void testRenameProjectUsingDropdown() {
        final String projectName = "This is the project to be renamed";
        createNewItemAndReturnToDashboard(this, projectName, Item.FREESTYLE_PROJECT);

        final String projectNewName = "Renamed project";
        openElementDropdown(this, getDriver().findElement(
                By.cssSelector(String.format("td a[href = 'job/%s/']", asURL(projectName)))));
        getDriver().findElement(DROPDOWN_RENAME).click();

        WebElement newName = getDriver().findElement(By.name("newName"));
        newName.clear();
        newName.sendKeys(projectNewName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div#main-panel h1")).getText(),
                projectNewName);
    }
}
