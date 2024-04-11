package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "NewFirstPipeline";
    private static final String PIPELINE_DESCRIPTION = "Description added to my pipeline.";
    private static final String RENAMED_PIPELINE_NAME = "RenamedFirstPipeline";
    private static final String EXISTED_JOB_XPATH = "//tr/td/a[@href='job/" + PIPELINE_NAME + "/']";

    private void createPipeline(String name){
        getDriver().findElement(By.xpath("//div[@class='task '][1]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void goToHomePage () {
        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs__list-item:nth-child(1)")).click();
    }
    
    @Test
    public void testCreatePipeline() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        String actualPipelineName = getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).getText();

        Assert.assertEquals(actualPipelineName, PIPELINE_NAME);
    }
    
    @Test
    public void testAddPipelineDescription() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver().findElement(By.xpath("//div[contains(text(),'" + PIPELINE_DESCRIPTION + "')]")).getText();

        Assert.assertTrue(actualDescription.contains(PIPELINE_DESCRIPTION));
    }

    @Test
    public void testRenamePipelineFromLeftMenu() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(RENAMED_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToHomePage();

        String changedPipelineName = getDriver().findElement(By.xpath("//tr/td/a[@href='job/" + RENAMED_PIPELINE_NAME + "/']")).getText();

        Assert.assertEquals(changedPipelineName,RENAMED_PIPELINE_NAME);
    }

    @Test
    public void testDeletePipelineFromLeftMenu() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        String startNewProjectMassage = getDriver().findElement(By.xpath("//h2")).getText();

        Assert.assertEquals(startNewProjectMassage,"Start building your software project");
    }
}

