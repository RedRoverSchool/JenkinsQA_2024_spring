package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DeletePipeline2Test extends BaseTest {
    @Test
    public void createPipeline() {
        getDriver().findElement(By.linkText("New Item")).click();
        WebElement field = getDriver().findElement(By.className("jenkins-input"));
        field.sendKeys("Pipeline1");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Наш новый пайплайн");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCancelPipeline() {
        createPipeline();

        getDriver().findElement(By.xpath("//a[@href='job/Pipeline1/']")).click();
        getDriver().findElement(By.xpath("//a[@href='#']")).click();

        Assert.assertEquals("Delete the Pipeline ‘Pipeline1’?", "Delete the Pipeline ‘Pipeline1’?");

        getDriver().findElement(By.xpath("//button[@data-id='cancel']")).click();
        getDriver().findElement(By.linkText("Pipeline1")).isDisplayed();
    }
    @Test
    public void testDeletePipeline() {
        createPipeline();

        getDriver().findElement(By.xpath("//a[@href='job/Pipeline1/']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[5]/span/a")).click();

        Assert.assertEquals("Delete the Pipeline ‘Pipeline1’?", "Delete the Pipeline ‘Pipeline1’?");

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

    }
}

