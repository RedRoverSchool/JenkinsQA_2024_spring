package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineConfigurationTest extends BaseTest {

    public static final By SAVE_BUTTON_CONFIGURATION = By.xpath("//button[@formnovalidate='formNoValidate']");

    public static final By TOGGLE_SWITCH_ENABLE_DISABLE = By.xpath("//label[@data-title='Disabled']");

    public void createPipline() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestCrazyTesters");
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowJob')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Ignore
    @Test
    public void testScroll() {
        createPipline();

        getDriver().findElement(By.xpath("//button[@data-section-id='pipeline']")).click();
        Assert.assertTrue(getDriver().findElement(By.id("bottom-sticker")).isDisplayed(), "Pipeline");
    }

    @Test
    public void testAddDescriptionInConfigureMenu() {
        final String pipelineDescription = "This description was added for testing purposes";

        createPipline();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelineDescription);
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//div[text()='" + pipelineDescription + "']")).isDisplayed(),
                "Something went wrong with the description");
    }

    @Test //should be refactored(1submit button, 2toggle)
    public void testDisableProjectInConfigureMenu() {
        final String expectedMessageForDisabledProject = "This project is currently disabled";

        createPipline();

        getDriver().findElement(By.xpath("//label[@data-title='Disabled']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.id("enable-project")).getText().contains(expectedMessageForDisabledProject));
    }

    @Test(dependsOnMethods = "testDisableProjectInConfigureMenu")
    public void testEnableProjectInConfigureMenu() {

        getDriver().findElement(By.xpath("//a[contains(@href, 'job')]")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        getDriver().findElement(TOGGLE_SWITCH_ENABLE_DISABLE).click();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).isDisplayed());
    }
}
