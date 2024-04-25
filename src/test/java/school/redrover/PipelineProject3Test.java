package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineProject3Test extends BaseTest {

    @Test
    public void testHideDescriptionPreview() {

        TestUtils.createJob(this, TestUtils.Job.PIPELINE, "Pipeline3");

        getDriver().findElement(By.name("description")).sendKeys("Pipeline description");
        getDriver().findElement(By.cssSelector(".textarea-show-preview")).click();
        getDriver().findElement(By.cssSelector(".textarea-hide-preview")).click();

        Assert.assertFalse(getDriver().findElement(By.cssSelector(".textarea-preview")).isDisplayed());
    }
}