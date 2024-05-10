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

        Assert.assertEquals(actualProjectName, projectName);

    }
}