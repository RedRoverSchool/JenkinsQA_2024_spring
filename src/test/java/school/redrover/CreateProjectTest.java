package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateProjectTest extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@it]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys("new Freestyle project");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate]")).click();

        WebElement newFreestyle = getDriver().findElement(By.xpath("//h1"));
        Assert.assertEquals(newFreestyle.getText(),"new Freestyle project");
    }
    @Test
    public void testCreatePipelineProject() {
        getDriver().findElement(By.xpath("//a[@it]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys("Pipeline");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate]")).click();

        WebElement pipeLine = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));
        Assert.assertEquals(pipeLine.getText(),"Pipeline");
    }
    @Test
    public void testEnableMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@it]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys("Muiltibranch Pipeline project");
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")).click();

        getDriver().findElement(By.xpath("//label[@data-title]")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate]")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        WebElement disable = getDriver().findElement(By.className("jenkins-button"));
        Assert.assertEquals(disable.getText(),"Add description");
    }
}
