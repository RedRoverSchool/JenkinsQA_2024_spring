package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProjectTest extends BaseTest {
    public static final String JOB_XPATH = "//*[text()='%s']";
    @Test
    public void testSameNamePipeline() {

        final String PROJECT_NAME = "Random pipeline";


        createJob(Job.PIPELINE,PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        goToJobPageAndEnterJobName(PROJECT_NAME);
       

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        // this line duplicates click on Pipeline, because of the Jenkins bug. Sometimes warning message doesn`t appear. Second click on Pipeline makes it happen.

        String warningMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(warningMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }



    @Test
    public void testCreationOfNewPipelineProject() throws InterruptedException {

        getDriver().findElement(By.linkText("Create a job")).click();
        String newJobUrl= getDriver().getCurrentUrl();
        Assert.assertTrue(newJobUrl.endsWith("/newJob"));
        Thread.sleep(500);
        Assert.assertTrue(getDriver().findElement(By.cssSelector("div#add-item-panel .h3")).isDisplayed());

        getDriver().findElement(By.id("name")).sendKeys("firstPipeline");
        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        newJobUrl= getDriver().getCurrentUrl();

        Assert.assertTrue(newJobUrl.endsWith("job/firstPipeline/configure"));

        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertTrue(getDriver().findElement(By.id("job_firstPipeline")).isDisplayed());

        WebElement jobInTableName = getDriver().findElement(By.cssSelector("a[href='job/firstPipeline/']"));
        Assert.assertEquals(jobInTableName.getText(),"firstPipeline");

    }

    private void goToJobPageAndEnterJobName(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
    }

    public void createJob(Job job, String jobName) {
        goToJobPageAndEnterJobName(jobName);
        getDriver().findElement(By.xpath(JOB_XPATH.formatted(job))).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    enum Job{
        FREESTYLE("Freestyle project"),
        PIPELINE("Pipeline"),
        MULTI_CONFIGURATION("Multi-configuration project");
//        FOLDER,
//        MULTI_BRUNCH_PIPELINE,
//        ORGANIZATION_FOLDER
        private final String jobName;

        Job(String jobName){
            this.jobName = jobName;
            }

        @Override
        public String toString() {
            return jobName;
        }
    }
}
