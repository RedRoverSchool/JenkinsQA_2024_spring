package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineConfigurationTest extends BaseTest {
    private static final String JOB_NAME = "TestCrazyTesters";

    public static final By SAVE_BUTTON_CONFIGURATION = By.xpath("//button[@formnovalidate='formNoValidate']");

    public static final By TOGGLE_SWITCH_ENABLE_DISABLE = By.xpath("//label[@data-title='Disabled']");

    public void createPipline() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOB_NAME);
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

    @Test
    public void testDisableProjectInConfigureMenu() {
        final String expectedMessageForDisabledProject = "This project is currently disabled";

        createPipline();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(TOGGLE_SWITCH_ENABLE_DISABLE));
        getDriver().findElement(TOGGLE_SWITCH_ENABLE_DISABLE).click();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

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

    @Ignore
    @Test
    public void testDiscardOldBuildsByCount() {
        createPipline();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text() = 'Discard old builds']"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name = '_.numToKeepStr']"))).sendKeys("1");
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-section-id='pipeline']"))).click();
        WebElement sampleScript = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'samples']//select")));
        Select sampleScriptSelect = new Select(sampleScript);
        sampleScriptSelect.selectByValue("hello");
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name = 'Submit']"))).click();

        WebElement buildButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-build-success = 'Build scheduled']")));
        buildButton.click();
        buildButton.click();
        getWait5().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@id = 'buildHistoryPage']//tr"), 1));
        getWait2().until(ExpectedConditions.invisibilityOfAllElements(getDriver().findElements(By.xpath("//td[contains(@class, 'progress-bar')]"))));
        getDriver().navigate().refresh();
        WebElement secondBuild = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class = 'build-row-cell']//a[text() = '#2']")));

        Assert.assertTrue(secondBuild.getAttribute("href").contains("/job/" + JOB_NAME.replaceAll(" ", "%20") + "/2/"), "there is no second build");
    }
}
