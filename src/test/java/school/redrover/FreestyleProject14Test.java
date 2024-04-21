package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;

public class FreestyleProject14Test extends BaseTest {

    @Test
    public void testCreateFreestyleProjectWithDescription() {

        final String FREESTYLE_PROJECT_NAME = "Random freestyle project";

        TestUtils.createJob(this, Job.FREESTYLE, FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.name(
                "description")).sendKeys("Some desc for Freestyle project");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath(
                "//span[text()='" + FREESTYLE_PROJECT_NAME + "']")).click();
        Boolean descriptionIsDisplayed = getDriver().findElement(By.id("description")).isDisplayed();

        Assert.assertEquals(descriptionIsDisplayed, true);
    }
}
