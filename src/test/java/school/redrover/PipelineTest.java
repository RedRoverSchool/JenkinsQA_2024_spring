package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;


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

    @Test
    public void testPipelineDescriptionTextAreaBacklightColor() {
        TestUtils.resetJenkinsTheme(this);
        TestUtils.goToMainPage(getDriver());

        createPipelineWithCreateAJob();
        getDriver().findElement(ADD_DESCRIPTION_LOCATOR).click();

        getWait2().until(ExpectedConditions.invisibilityOfElementLocated(ADD_DESCRIPTION_LOCATOR));
        String currentTextAreaBorderBacklightColor = getDriver().switchTo().activeElement().
                getCssValue("box-shadow").split(" 0px")[0];

        Assert.assertEquals(currentTextAreaBorderBacklightColor, "rgba(11, 106, 162, 0.25)",
                "Current text area border backlight color is not equal to rgba(11, 106, 162, 0.25)");
    }

    @Test
    public void testPipelineDescriptionTextAreaBacklightDefaultColor() {
        TestUtils.resetJenkinsTheme(this);
        TestUtils.goToMainPage(getDriver());

        createPipelineWithCreateAJob();
        getDriver().findElement(ADD_DESCRIPTION_LOCATOR).click();
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String defaultTextAreaBorderBacklightColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                getDriver().findElement(By.name("description")));

        Assert.assertEquals(defaultTextAreaBorderBacklightColor, "rgba(11,106,162,.25)");
    }

    @Test
    public void testYesButtonColorDeletingPipelineInSidebar() {
        TestUtils.resetJenkinsTheme(this);
        TestUtils.goToMainPage(getDriver());

        createPipelineWithCreateAJob();
        getDriver().findElement(By.cssSelector("[data-title='Delete Pipeline']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String okButtonHexColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--color');",
                getDriver().findElement(By.xpath("//button[@data-id='ok']")));

        Assert.assertEquals(okButtonHexColor, "#e6001f", "The confirmation button color is not red");
    }
}
