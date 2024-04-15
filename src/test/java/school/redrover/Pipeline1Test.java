package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline1Test extends BaseTest {
    private static final String PIPELINE_NAME = "NewPipeline";

    private void createPipeline (String pipelineName){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
    }
    @Test
    public void testCreatePipeline() {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[normalize-space()='NewPipeline']")).getText(),
                PIPELINE_NAME);
    }
    @Test
    public void testCreatePipelineWithSameName() {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        createPipeline(PIPELINE_NAME);

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» A job already exists with the name ‘NewPipeline’");

    }
}


