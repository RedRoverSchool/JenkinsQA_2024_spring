package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "FirstPipeline";
    private static final By ADD_DESCRIPTION_LOCATOR = By.id("description-link");

    private void createPipelineWithCreateAJob() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Ignore
    @Test
    public void testPipelineDescriptionTextAreaBacklightColor() {
        createPipelineWithCreateAJob();
        getDriver().findElement(ADD_DESCRIPTION_LOCATOR).click();

        getWait2().until(ExpectedConditions.invisibilityOfElementLocated(ADD_DESCRIPTION_LOCATOR));
        String currentTextAreaBorderBacklightColor = getDriver().switchTo().activeElement().
                getCssValue("box-shadow").split(" 0px")[0];

        Assert.assertEquals(currentTextAreaBorderBacklightColor, "rgba(11, 106, 162, 0.25)",
                "Current text area border backlight color is not equal to rgba(11, 106, 162, 0.25)");
    }

    @Ignore
    @Test
    public void testPipelineDescriptionTextAreaBacklightDefaultColor() {
        createPipelineWithCreateAJob();
        getDriver().findElement(ADD_DESCRIPTION_LOCATOR).click();
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String defaultTextAreaBorderBacklightColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                getDriver().findElement(By.name("description")));

        Assert.assertEquals(defaultTextAreaBorderBacklightColor, "rgba(11,106,162,.25)");
    }
}
