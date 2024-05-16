package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject8Test extends BaseTest {

    @Test
    public void testCreateProject() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Freestyle project")
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, "Freestyle project");
    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testRenamingFreestyleProject() {

        final String newName = "New name";

        String projectName = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickRename()
                .setNewName(newName)
                .clickRename()
                .getProjectName();

        Assert.assertEquals(projectName, newName);
    }
}
