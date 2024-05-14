package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static org.testng.Assert.*;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Ignore
    @Test
    public void testCreatExistingFreestyleProject() {

        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECT_NAME);

        assertTrue(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());
    }

    @Test
    public void testAddProjectDescription() {

        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);
        TestUtils.addProjectDescription(this, PROJECT_NAME, "Project Description");

        assertTrue(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).isDisplayed());
    }

    @Test
    public void testRenameProjectToSameName() {

        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);
        TestUtils.renameItem(this, PROJECT_NAME, PROJECT_NAME);

        assertTrue(getDriver().findElement(By.xpath("//*[text()='Error']")).isDisplayed());
    }

    @Test
    public void testDeleteProject() {

        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);
        TestUtils.deleteItem(this, PROJECT_NAME);

        assertTrue(getDriver().findElement(By.className("empty-state-block")).isDisplayed());
    }

    @Test
    public void testCreateNewProject() {

        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);

        assertEquals(getDriver().findElement(By.linkText(PROJECT_NAME)).getText(), PROJECT_NAME);
    }
}
