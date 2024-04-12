package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "NewFirstPipeline";
    private static final String PIPELINE_DESCRIPTION = "Description added to my pipeline.";
    private static final String RENAMED_PIPELINE_NAME = "RenamedFirstPipeline";
    private static final String EXISTED_JOB_XPATH = "//a[starts-with(@class,'jenkins-table__link')]";

    private void createPipeline(String name) {
        getDriver().findElement(By.xpath("//div[@class='task '][1]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void goToHomePage() {
        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs__list-item:nth-child(1)")).click();
    }

    @Test
    public void testCreatePipeline() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();

        String actualPipelineName = getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText();

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

        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']//div[1]")).getText();

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
        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();

        String changedPipelineName = getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText();

        Assert.assertEquals(changedPipelineName, RENAMED_PIPELINE_NAME);
    }

    @Test
    public void testDeletePipelineFromLeftMenu() {
        createPipeline(PIPELINE_NAME);
        goToHomePage();

        getDriver().findElement(By.xpath(EXISTED_JOB_XPATH)).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        String startNewProjectMassage = getDriver().findElement(By.xpath("//h2")).getText();

        Assert.assertEquals(startNewProjectMassage, "Start building your software project");
    }
}

