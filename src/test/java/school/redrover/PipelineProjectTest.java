package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;

public class PipelineProjectTest extends BaseTest {

    public static final String JOB_XPATH = "//*[text()='%s']";

    @Ignore
    @Test
    public void testSameNamePipeline() {

        final String PROJECT_NAME = "Random pipeline";

        TestUtils.createJob(this, Job.PIPELINE, PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        TestUtils.goToJobPageAndEnterJobName(this, PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        // this line duplicates click on Pipeline, because of the Jenkins bug. Sometimes warning message doesn`t appear. Second click on Pipeline makes it happen.

        String warningMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(warningMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }

    @Test
    public void testCreationOfNewPipelineProject() throws InterruptedException {

        getDriver().findElement(By.linkText("Create a job")).click();
        String newJobUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(newJobUrl.endsWith("/newJob"));

        WebElement inputElement = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        inputElement.sendKeys("firstPipeline");

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        newJobUrl = getDriver().getCurrentUrl();

        Assert.assertTrue(newJobUrl.endsWith("job/firstPipeline/configure"));

        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertTrue(getDriver().findElement(By.id("job_firstPipeline")).isDisplayed());

        WebElement jobInTableName = getDriver().findElement(By.cssSelector("a[href='job/firstPipeline/']"));
        Assert.assertEquals(jobInTableName.getText(), "firstPipeline");
    }

    @Test
    public void testAddDescriptionPreview(){

        TestUtils.createJob(this, Job.PIPELINE, "Pipeline project");
        
        getDriver().findElement(By.xpath("//*[text()='Pipeline project']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("First");
        getDriver().findElement(By.xpath("//a[@class='textarea-show-preview']")).click();

        WebElement previewDescription = getDriver().findElement(By.xpath("//div[@class='textarea-preview']"));

        Assert.assertEquals(previewDescription.getText(),"First");
    }
}
