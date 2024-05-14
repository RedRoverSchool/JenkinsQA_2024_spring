package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject4Test extends BaseTest {

    private static final String PROJECT_NAME = "JavaHashGroupProject";

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
                .deleteFreestyleProject()
                .confirmDeleteFreestyleProject()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }

    @Test
    public void testCreateNewFreestyleProjectWithDescription() {
        final String projectItemDescription = "This is first Project";

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(projectItemDescription)
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), PROJECT_NAME);
        Assert.assertEquals(freestyleProjectPage.getProjectDescriptionText(), projectItemDescription);

        List<String> itemList = freestyleProjectPage
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(PROJECT_NAME));
    }
}
