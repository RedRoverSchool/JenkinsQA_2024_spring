package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemPage;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineProject22Test extends BaseTest {
   private String projectName;
    @Test
    public void testCreatePipeline() {
        projectName = TestUtils.getUniqueName("testPipeline");

        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, projectName);
    }

    @Test(dependsOnMethods = {"testCreatePipeline"})
    public void testCreatePipelineHomePageView() {
        List<String> items = new HomePage(getDriver())
                .getItemList();

        Assert.assertTrue(items.contains(projectName));
    }

    @Test(dependsOnMethods = {"testCreatePipeline"})
    public void testCreatePipelineCheckBySearch() {
        String actualProjectName = new HomePage(getDriver())
                .searchPipelineProject(projectName)
                .getProjectName();

        Assert.assertEquals(actualProjectName,projectName);
    }

    @Test(dependsOnMethods = {"testCreatePipeline"})
    public void testCreatePipelineCheckRedirectFromDashboard() {
        String actualProjectName = new HomePage(getDriver())
                .redirectPipelinePage(projectName)
                .getProjectName();

        Assert.assertEquals(actualProjectName,projectName);
    }

    @Test(dependsOnMethods = {"testCreatePipeline"})
    public void testCreatePipelineWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectPipeline()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorMessage.contains("A job already exists with the name"));
    }

    @Test
    public void testCreatePipelineWithEmptyName() {
        Boolean errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .selectPipeline()
                .getOkButtoneState();

        Assert.assertFalse(errorMessage);
    }

    @Test
    public void testCreatePipelineWithSpacesName() {
        projectName = "     ";

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectPipeline()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreatePipelineWithSpecialSymbol() {
        projectName = TestUtils.getUniqueName("testPipeline/");

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectPipeline()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreatePipelineWithLongestName() {
        for (int i = 0; i < 260; i++) {
            projectName += "a";
        }

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectPipeline()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("Logging ID="));
    }
}