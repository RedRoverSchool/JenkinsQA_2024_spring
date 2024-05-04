package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;

public class PipelineChanges extends BaseTest {
    @Test
    public void testViewPipelineChanges() {
        TestUtils.createItem(TestUtils.PIPELINE, "newtest", this);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tasks']/div[2]"))).click();
        WebElement element = getDriver().findElement(By.xpath("//div[text()='No changes in any of the builds, or multiple SCMs in use.']"));

        Assert.assertNotNull(element);
    }
}
