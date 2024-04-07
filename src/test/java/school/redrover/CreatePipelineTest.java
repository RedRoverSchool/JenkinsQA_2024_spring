package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatePipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "New Pipeline";
    private static final String PIPELINE_DESCRIPTION = "Pipeline Description";

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text()= 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement description = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        description.click();
        description.sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        String savedPipelineName = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText();

        Assert.assertEquals(savedPipelineName, "Pipeline " + PIPELINE_NAME);
    }
}
