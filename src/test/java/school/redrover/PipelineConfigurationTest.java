package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "TestCrazyTesters";

    public static final By SAVE_BUTTON_CONFIGURATION = By.xpath("//button[@formnovalidate='formNoValidate']");

    public static final By TOGGLE_SWITCH_ENABLE_DISABLE = By.xpath("//label[@data-title='Disabled']");

    public void createPipeline() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOB_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowJob')]")).click();
        getWait60().until(ExpectedConditions.visibilityOfElementLocated(By.id("ok-button")));
        getDriver().findElement(By.id("ok-button")).click();
    }

    public void navigateToConfigurePageFromDashboard() {
        getDriver().findElement(By.xpath("//a[contains(@href, '" + JOB_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
    }

    @Ignore
    @Test
    public void testScroll() {
        createPipeline();

        getDriver().findElement(By.xpath("//button[@data-section-id='pipeline']")).click();
        Assert.assertTrue(getDriver().findElement(By.id("bottom-sticker")).isDisplayed(), "Pipeline");
    }

    @Test
    public void testAddDescriptionInConfigureMenu() {
        final String pipelineDescription = "This description was added for testing purposes";

        createPipeline();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelineDescription);
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//div[text()='" + pipelineDescription + "']")).isDisplayed(),
                "Something went wrong with the description");
    }

    @Test
    public void testDisableProjectInConfigureMenu() {
        final String expectedMessageForDisabledProject = "This project is currently disabled";

        createPipeline();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(TOGGLE_SWITCH_ENABLE_DISABLE));
        getDriver().findElement(TOGGLE_SWITCH_ENABLE_DISABLE).click();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(
                getDriver().findElement(By.id("enable-project")).getText().contains(expectedMessageForDisabledProject));
    }

    @Test(dependsOnMethods = "testDisableProjectInConfigureMenu")
    public void testEnableProjectInConfigureMenu() {

        navigateToConfigurePageFromDashboard();

        getDriver().findElement(TOGGLE_SWITCH_ENABLE_DISABLE).click();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).isDisplayed());
    }

    @Test
    public void testDiscardOldBuildsByCount() {
        createPipeline();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text() = 'Discard old builds']"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name = '_.numToKeepStr']"))).sendKeys("1");
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-section-id='pipeline']"))).click();
        WebElement sampleScript = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'samples']//select")));
        Select sampleScriptSelect = new Select(sampleScript);
        sampleScriptSelect.selectByValue("hello");
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name = 'Submit']"))).click();

        WebElement buildButton = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-build-success = 'Build scheduled']")));
        buildButton.click();
        buildButton.click();
        getWait5().until(ExpectedConditions.invisibilityOfAllElements(getDriver().findElements(By.xpath("//td[contains(@class, 'progress-bar')]"))));
        getDriver().navigate().refresh();
        WebElement secondBuild = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class = 'build-row-cell']//a[text() = '#2']")));

        Assert.assertTrue(secondBuild.getAttribute("href").contains("/job/" + JOB_NAME.replaceAll(" ", "%20") + "/2/"), "there is no second build");
    }

    @Test
    public void testSectionsOfSidePanelAreVisible() {

        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Configure')]")));

        List<WebElement> sections = List.of(
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'general')]")),
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'advanced-project-options')]")),
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'pipeline')]")));

        for (WebElement section : sections) {
            Assert.assertTrue(section.isDisplayed(),
                    "The requested section is not found in Configure side-panel");
        }
    }

    @Test
    public void testAddDisplayNameInAdvancedSection() {
        final String displayNameText = "This is project's Display name text for Advanced Project Options";
        final By advancedButton = By.xpath("//section[@class='jenkins-section']//button[@type='button']");

        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-section-id='advanced-project-options']"))).click();

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                getDriver().findElement(advancedButton));

        getWait10().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='setting-main']//input[contains(@checkurl, 'checkDisplayName')]")))
                .sendKeys(displayNameText);

        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                displayNameText);
    }
}
