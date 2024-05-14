package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;


public class FreestyleProject17Test extends BaseTest {

    private final String JOB_NAME = "YS_jenkins_ui";

    @Test
    void testCreateNewFreestyleProject() {
        final String projectDescription = "This test is trying to create a new freestyle job";

        String projectName = new HomePage(getDriver())
                .clickNewItem().setItemName(JOB_NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .setDescription(projectDescription)
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, JOB_NAME);
    }

    @Test
    public void testAddDescriptionOfConfiguration() {
        final String projectDescription = "This test is trying to create a new freestyle job";

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem().setItemName(JOB_NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .setDescription(projectDescription)
                .clickSaveButton()
                .clickConfigure()
                .clearAndSetDescription("Description of " + JOB_NAME)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, "Description of " + JOB_NAME);
    }
}