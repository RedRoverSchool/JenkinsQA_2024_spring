package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.MultibranchPipelineConfigPage;
import school.redrover.runner.BaseTest;

import school.redrover.runner.TestUtils;

public class NewItem17Test extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectDG";

    @Test
    public void testCreateNewItemFP() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, '_FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String configurationHeaderH1 = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(configurationHeaderH1, "Configure");

        String configurationPageBreadcrumbs = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]")).getText();
        Assert.assertEquals(configurationPageBreadcrumbs, "Configuration");

        String sourceCodeManagementBlock = getDriver().findElement(By.xpath("//*[@id='source-code-management']")).getText();
        Assert.assertEquals(sourceCodeManagementBlock, "Source Code Management");

    }

    @Test
    public void testCreateNewItemFPTU() {
        TestUtils.createProjectItem(TestUtils.ProjectType.FREESTYLE_PROJECT, this, new FreestyleConfigPage(getDriver()), PROJECT_NAME, true);
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        
        String configurationHeaderH1 = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(configurationHeaderH1, PROJECT_NAME);
    }
}
