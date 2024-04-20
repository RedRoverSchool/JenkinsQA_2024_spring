package school.redrover;

import org.checkerframework.checker.index.qual.IndexFor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Pipeline1Test extends BaseTest {
    private static final String PIPELINE_NAME = "NewPipeline";

    private void createPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
    }

    private void chooseProjectAndClick(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/"
                + projectName.replaceAll(" ", "%20") + "/']"))).click();
    }

    private void clickFullStageViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//a[contains(@href, 'workflow-stage')]")))).click();
    }

    private String getH2HeaderText() {
        return getDriver().findElement(By.xpath("//h2")).getText();
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

    @Ignore
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

    @Test
    public void testVisibilityDisableButton() {
        TestUtils.createNewJob(this, TestUtils.Job.PIPELINE, "Pipeline1");
        getDriver().findElement(By.xpath("//table//a[@href='job/Pipeline1/']")).click();

        Assert.assertTrue(getDriver().findElement(By.name("Submit")).isDisplayed());

        getDriver().findElement(By.name("Submit")).click();

        String actualStatusMessage = getDriver().findElement(By.id("enable-project")).getAttribute("innerText");

        Assert.assertTrue(actualStatusMessage.contains("This project is currently disabled"));
    }

    @Test
    public void testFullStageViewButton() {
        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);
        TestUtils.goToMainPage(getDriver());

        String expectedResult = PIPELINE_NAME + " - Stage View";

        chooseProjectAndClick(PIPELINE_NAME);

        clickFullStageViewButton();

        Assert.assertEquals(getH2HeaderText(), expectedResult);
    }
}

