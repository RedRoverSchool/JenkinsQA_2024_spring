package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class DeletePipelineTest extends BaseTest {
    final String pipelineName = "DeletePipeline";

    @Test
    public void testDeletePipelineSideMenu() {
        TestUtils.createJob(this, TestUtils.Job.PIPELINE, pipelineName);

        TestUtils.goToMainPage(getDriver());
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//a[@href='job/"
                + pipelineName + "/']"))).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + pipelineName + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }

    @Test
    public void testDeletePipelineDropdown() {
        TestUtils.createJob(this, TestUtils.Job.PIPELINE, pipelineName);
        TestUtils.goToMainPage(getDriver());

        TestUtils.deleteJobViaDropdowm(this, pipelineName);

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + pipelineName + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }
}
