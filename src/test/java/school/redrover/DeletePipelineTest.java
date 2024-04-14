package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class DeletePipelineTest extends BaseTest {
    public void createPipeline(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testDeletePipelineSideMenu() {
        String pipelineName = "DeletePipeline";

        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//table//a[@href='job/" + pipelineName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + pipelineName + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }
}
