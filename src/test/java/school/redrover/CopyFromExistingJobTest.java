package school.redrover;

import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;

    public class CopyFromExistingJobTest extends BaseTest{
         @Test
         public void testCopyFromExistingJob() {
             TestUtils.createNewJob(this, Job.PIPELINE, "ppp");
             TestUtils.createNewJob(this, Job.FREESTYLE, "fff");
             TestUtils.createNewJob(this, Job.FOLDER, "Folder1");

             String notExistingName ="AAA";

             getDriver().findElement(By.cssSelector("a[href$='newJob']")).click();
             getDriver().findElement(By.id("name")).sendKeys("someName");
             getDriver().findElement(By.id("from")).sendKeys(notExistingName);
             getDriver().findElement(By.id("ok-button")).click();
             String newJobUrl = getDriver().getCurrentUrl();
             String headerError = getDriver().findElement(By.cssSelector("#main-panel h1")).getText();
             String errorText  = getDriver().findElement(By.cssSelector("#main-panel p")).getText();

             Assert.assertTrue(newJobUrl.endsWith("/createItem"));
             Assert.assertEquals(headerError,"Error");
             Assert.assertEquals(errorText,"No such job: " + notExistingName);
         }
    }