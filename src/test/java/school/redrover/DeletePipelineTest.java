package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class DeletePipelineTest extends BaseTest {
    final String pipelineName = "DeletePipeline";

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
        createPipeline(pipelineName);

        getDriver().findElement(By.xpath("//table//a[@href='job/" + pipelineName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + pipelineName + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }

    @Test
    public void testDeletePipelineDropdown() throws InterruptedException {
        Actions action = new Actions(getDriver());
        createPipeline(pipelineName);

        action.moveToElement(getDriver()
                .findElement(By.xpath("//table//a[@href='job/" + pipelineName + "/']"))).perform();
        action.moveToElement(getDriver()
                .findElement(By.cssSelector("[data-href$='/job/" + pipelineName + "/']")))
                .perform();

        getDriver().findElement(By.cssSelector("[data-href$='/job/" + pipelineName + "/']")).click();

        getDriver().findElement(By.xpath("//button[@href='/job/" + pipelineName + "/doDelete']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + pipelineName + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }
}
