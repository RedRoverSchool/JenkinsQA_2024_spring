package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineProject7Test extends BaseTest {

    @Test
    public void testCreate() {
        getDriver().findElement(By.linkText("New Item")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("name"))).sendKeys("ProjectName");
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[text() = 'OK']")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        TestUtils.goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(By.linkText("ProjectName")).isDisplayed());
    }
}
