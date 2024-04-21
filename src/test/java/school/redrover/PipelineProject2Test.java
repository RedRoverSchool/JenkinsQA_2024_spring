package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class PipelineProject2Test extends BaseTest {

    @Ignore
    @Test
    public void testAddingDescriptionToThePipeline() {
        final String description = "Imagine Dragons";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("pipelineName");
        getDriver().findElement(By.cssSelector("[class*='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String descriptionText = getDriver().findElement(By.cssSelector("#description div:nth-child(1)")).getText();
        Assert.assertEquals(descriptionText, description);
    }
}
