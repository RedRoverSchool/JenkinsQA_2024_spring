package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject4Test extends BaseTest {

    private static final String PROJECT_NAME = "JavaHashGroupProject";

    private final String projectItemDescription = "This is first Project";

    @Test
    public void testCreateNewFreestyleProject() {

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(freestyleProjectPage.isProjectNameDisplayed());
        Assert.assertEquals(freestyleProjectPage.getProjectName(), PROJECT_NAME);
    }

    @Test
    public void testDeleteNewFreestyleProject2() {

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedItemName()
                .clickDelete()
                .clickYesInConfirmDeleteDialog()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }


    @Test
    public void testCreateNewFreestyleProjectWithDescription () {

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(projectItemDescription)
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), PROJECT_NAME);
        Assert.assertEquals(freestyleProjectPage.getProjectDescriptionText(), projectItemDescription);
    }

    @Test (dependsOnMethods = "testCreateNewFreestyleProjectWithDescription")
    public void testCheckExistedNewFreestyleProject() {

        List<String> itemList = new HomePage(getDriver())
                .getItemList();

        Assert.assertTrue((itemList.contains(PROJECT_NAME)));
    }

    @Test (dependsOnMethods = "testCreateNewFreestyleProjectWithDescription")
    public void testCheckNewFreestyleProjectDescription() {

        String description = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .getProjectDescriptionText();

        Assert.assertEquals(description, projectItemDescription);
    }
}
