package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineAddDescriptionTest extends BaseTest{

    @Test

    public void testButtonBackgroundColor () {
        String expectedColor = "rgba(175,175,207,.175)";

        getDriver().findElement(By.cssSelector("a[href$=\"/newJob\"]")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Pipeline");
        getDriver().findElement(By.xpath("//li[contains(@class, '_Folder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String actualColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--item-background--hover');",
                getDriver().findElement(By.id("description-link")));

        Assert.assertEquals(actualColor, expectedColor);
    }
}
