package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateProjectTest extends BaseTest {

    private static final String BUTTON = "//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']";
    private static final String MAIN_PAGE = "//a[@it]";
    private static final String PROJECT_BUTTON = "//button[@formnovalidate]";
    private static final String NAME_SELECTOR = "#name";

    @Test
    public void testCreateFreestyleProject() {
        getDriver().findElement(By.xpath(MAIN_PAGE)).click();

        getDriver().findElement(By.cssSelector(NAME_SELECTOR)).sendKeys("new Freestyle project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath(BUTTON)).click();

        getDriver().findElement(By.xpath(PROJECT_BUTTON)).click();

        WebElement newFreestyle = getDriver().findElement(By.xpath("//h1"));

        Assert.assertEquals(newFreestyle.getText(),"new Freestyle project");
    }

    @Test
    public void testCreatePipelineProject() {
        getDriver().findElement(By.xpath(MAIN_PAGE)).click();

        getDriver().findElement(By.cssSelector(NAME_SELECTOR)).sendKeys("Pipeline");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.xpath(BUTTON)).click();
        getDriver().findElement(By.xpath(PROJECT_BUTTON)).click();

        WebElement pipeLine = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));

        Assert.assertEquals(pipeLine.getText(),"Pipeline");
    }

    @Test
    public void testEnableMultibranchPipeline() {
        getDriver().findElement(By.xpath(MAIN_PAGE)).click();

        getDriver().findElement(By.cssSelector(NAME_SELECTOR)).sendKeys("Muiltibranch Pipeline project");
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath(BUTTON)).click();

        getDriver().findElement(By.xpath("//label[@data-title]")).click();

        getDriver().findElement(By.xpath(PROJECT_BUTTON)).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        WebElement disable = getDriver().findElement(By.className("jenkins-button"));

        Assert.assertEquals(disable.getText(),"Add description");
    }
}
