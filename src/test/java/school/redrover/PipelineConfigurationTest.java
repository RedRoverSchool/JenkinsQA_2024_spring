package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineConfigurationTest extends BaseTest {
    public void createPipline() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestCrazyTesters");
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowJob')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testScroll() {
        createPipline();

        getDriver().findElement(By.xpath("//button[@data-section-id='pipeline']")).click();
        Assert.assertTrue(getDriver().findElement(By.id("bottom-sticker")).isDisplayed(), "Pipeline");
    }
}
