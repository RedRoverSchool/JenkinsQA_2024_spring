package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

import static org.testng.Assert.assertTrue;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Ignore
    @Test
    public void testCreatExistingFreestyleProject() {

        createNewItemAndReturnToDashboard(this, PROJECT_NAME, Item.FREESTYLE_PROJECT);
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECT_NAME);

        assertTrue(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());
    }

    @Ignore
    @Test
    public void testAddProjectDescription() {

        createNewItemAndReturnToDashboard(this, PROJECT_NAME, Item.FREESTYLE_PROJECT);
        addProjectDescription(this, PROJECT_NAME, "Project Description");

        assertTrue(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).isDisplayed());
    }

    @Test
    public void testRenameProjectToSameName() {

        createNewItemAndReturnToDashboard(this, PROJECT_NAME, Item.FREESTYLE_PROJECT);
        renameItem(this, PROJECT_NAME, PROJECT_NAME);

        assertTrue(getDriver().findElement(By.xpath("//*[text()='Error']")).isDisplayed());
    }

    @Test
    public void testDeleteProject() {

        createNewItemAndReturnToDashboard(this, PROJECT_NAME, Item.FREESTYLE_PROJECT);
        deleteItem(this, PROJECT_NAME);

        assertTrue(getDriver().findElement(By.className("empty-state-block")).isDisplayed());
    }
}