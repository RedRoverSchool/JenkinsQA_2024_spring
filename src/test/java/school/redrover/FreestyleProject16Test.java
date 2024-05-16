package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FreestyleProject16Test extends BaseTest {

    final String PROJECT_NAME = "MD first project";
    final String NEW_PROJECT_NAME = "Rename MD first project";
    final String PROJECT_DESCRIPTION = "I like my project!";

    @Test
    public void testCreateFirstTest() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, PROJECT_NAME);
    }

    @Test
    public void testCreateFirstTestOne() {

        String generalText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickConfigure()
                .getGeneralText();

        Assert.assertEquals(generalText, "General");
    }

    @Test
    public void testAddDescription() {

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testCreateFirstTest"})
    public void testRenameFirstProject() {

        String newProjectName = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickRename()
                .setNewName(NEW_PROJECT_NAME)
                .clickRename()
                .getProjectName();

        Assert.assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRenameFirstProject")
    public void testDeleteFirstProject() {

        String errorText = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .searchProjectByName(NEW_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .getErrorText();

        Assert.assertEquals(errorText, "Nothing seems to match.");
    }

    @Test
    public void testCreateFreestyleProject() {
        final String freestyleProjectName = "FreestyleProjectTest";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(freestyleProjectName));
    }
}
