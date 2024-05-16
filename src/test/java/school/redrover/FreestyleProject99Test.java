package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Test
    public void testCreatExistingFreestyleProject() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .isErrorItemNameInvalidDisplayed());
    }

    @Test
    public void testAddProjectDescription() {

        String projectDescription = "Project Description";

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(projectDescription)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, projectDescription);
    }

    @Test
    public void testRenameProjectToSameName() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickRename()
                .setNewName(PROJECT_NAME)
                .clickRenameAnyway()
                .isErrorMessageDisplayed());
    }

    @Test
    public void testDeleteProject() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .clickLogo()
                .getItemList()
                .isEmpty());
    }

    @Test
    public void testCreateProject() {

        Assert.assertFalse(new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList()
                .isEmpty());
    }
}
