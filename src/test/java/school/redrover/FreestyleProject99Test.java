package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Ignore
    @Test
    public void testCreatExistingFreestyleProject() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECT_NAME);

        Assert.assertTrue(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());
    }

    @Test
    public void testAddProjectDescription() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);
        TestUtils.addProjectDescription(this, PROJECT_NAME, "Project Description");

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).isDisplayed());
    }

    @Test
    public void testRenameProjectToSameName() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);
        TestUtils.renameItem(this, PROJECT_NAME, PROJECT_NAME);

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Error']")).isDisplayed());
    }

    @Test
    public void testDeleteProject() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);
        TestUtils.deleteItem(this, PROJECT_NAME);

        Assert.assertTrue(getDriver().findElement(By.className("empty-state-block")).isDisplayed());
    }

    @Test
    public void testCreateNewProject() {

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);

        Assert.assertEquals(getDriver().findElement(By.linkText(PROJECT_NAME)).getText(), PROJECT_NAME);
    }
}
