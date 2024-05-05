package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "TestCrazyTesters";

    private static final By SAVE_BUTTON_CONFIGURATION = By.xpath("//button[@formnovalidate='formNoValidate']");

    private static final By TOGGLE_SWITCH_ENABLE_DISABLE = By.xpath("//label[@data-title='Disabled']");

    private static final By ADVANCED_PROJECT_OPTIONS_MENU = By.xpath("//button[@data-section-id='advanced-project-options']");

    private static final By DISPLAY_NAME_TEXT_FIELD = By.xpath("//div[@class='setting-main']//input[contains(@checkurl, 'checkDisplayName')]");

    private final String displayNameText = "This is project's Display name text for Advanced Project Options";

    private Actions actions;

    private Actions getActions() {
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }

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

    public void clickOnAdvancedButton() {
        WebElement advancedButton = getDriver().findElement(By.xpath("//section[@class='jenkins-section']//button[@type='button']"));

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
               advancedButton);
    }

    public void scrollCheckBoxQuietPeriodIsVisible(){
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//label[text()='Poll SCM']")));
    }

    public void scrollCheckBoxThrottleBuildsIsVisible() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getDriver().findElement(By.xpath("//label[text()='Throttle builds']")));
    }

    @Test
    public void testScroll() {
        createPipeline();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-section-id='pipeline']"))).click();

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

    @Ignore
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

    @Test(dependsOnMethods = "testAddDescriptionInConfigureMenu")
    public void testEditDiscription() {

        getDriver().findElement(By.id("description-link")).click();
        WebElement textArea = getDriver().findElement(By.name("description"));
        textArea.clear();
        getActions().click(textArea)
                .keyDown(Keys.SHIFT)
                .sendKeys("project")
                .keyUp(Keys.SHIFT)
                .perform();

        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[text()='PROJECT']")).isDisplayed(),
                "PROJECT");
    }

    @Test
    public void testAddDisplayNameInAdvancedSection() {
        final String displayNameText = "This is project's Display name text for Advanced Project Options";

        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD)).sendKeys(displayNameText);
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                displayNameText);
    }

    @Test (dependsOnMethods = "testAddDisplayNameInAdvancedSection")
    public void testEditDisplayNameInAdvancedSection() {
        final String editedDisplayNameText = " - EDITED";

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD));
        getDriver().findElement(DISPLAY_NAME_TEXT_FIELD).sendKeys(editedDisplayNameText);
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(SAVE_BUTTON_CONFIGURATION));
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText().contains(editedDisplayNameText),"Your DisplayName is not edited correctly");
    }

    @Test
    public void testVerifySectionHasTooltip(){
        String labelText = "Display Name";
        String tooltipText = "Help for feature: Display Name";
        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        clickOnAdvancedButton();

        String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + labelText + "')]//a")).getAttribute("tooltip");

        Assert.assertEquals(actualTooltip, tooltipText);
    }
    @Test
    public void testChoosePipelineScript() {
        createPipeline();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-section-id='pipeline']"))).click();

        WebElement selectScript = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'samples']//select")));
        Select simpleDropDown = new Select(selectScript);
        simpleDropDown.selectByValue("github-maven");

        WebElement uncheckCheckBox = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Use Groovy Sandbox']")));
        uncheckCheckBox.click();

        WebElement link = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@target='blank']")));
        Assert.assertTrue(link.isDisplayed(), "Uncheck doesn't work");
    }

    @Test (dependsOnMethods = {"testAddDisplayNameInAdvancedSection", "testEditDisplayNameInAdvancedSection"})
    public void testDeleteDisplayNameInAdvancedSection() {
        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD)).clear();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertFalse(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText().contains(displayNameText));
    }

    @Test
    public void testSetQuietPeriodBuildTriggers() {
        final int numberOfSeconds = 3;

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        navigateToConfigurePageFromDashboard();
        scrollCheckBoxQuietPeriodIsVisible();

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='quiet_period']"))
                        .getAttribute("value").contains("" + numberOfSeconds + ""),
                "The actual numberOfSeconds differs from expected result");
    }

    @Test
    public void testVerifySectionsHaveTooltips() {
        String[] labelsText = {"Display Name", "Script"};

        createPipeline();
        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        clickOnAdvancedButton();

        for (String label: labelsText) {
            String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + label + "')]//a")).getAttribute("tooltip");

            Assert.assertEquals(actualTooltip, "Help for feature: " + label);
        }
    }

    @Test
    public void testSetQuietPeriodBuildTriggersLessThanZero() {
        final int numberOfSeconds = -5;
        final String errorMessage = "This value should be larger than 0";

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(By.xpath("//div[text()='Number of seconds']")).click();

        WebElement errorElement = getDriver().findElement(By.xpath("//div[@class='form-container tr']//div[@class='error']"));
        getWait5().until(ExpectedConditions.visibilityOf(errorElement));

        Assert.assertEquals(errorElement.getText(), errorMessage);
    }

    @Test
    public void testSetDoubleQuietPeriodBuildTriggersLessThanZero() {
        final double numberOfSeconds = 0.3;
        final String errorMessage = "Not an integer";

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(By.xpath("//div[text()='Number of seconds']")).click();

        WebElement errorElement = getDriver().findElement(By.xpath("//div[@class='form-container tr']//div[@class='error']"));
        getWait5().until(ExpectedConditions.visibilityOf(errorElement));

        Assert.assertEquals(errorElement.getText(), errorMessage);
    }

    @Test
    public void testSetPipelineSpeedDurabilityOverride() {
        final String selectedOptionForCheck = "Less durability, a bit faster (specialty use only)";
        createPipeline();
        navigateToConfigurePageFromDashboard();

        getDriver().findElement(By.xpath("//label[text()='Pipeline speed/durability override']")).click();
        WebElement selectCustomPipelineSpeedDurabilityLevel = getDriver().findElement(By.xpath("//select[@class='setting-input']"));
        Select dropDown = new Select(selectCustomPipelineSpeedDurabilityLevel);
        dropDown.selectByIndex(1);
        String selectedValue = selectCustomPipelineSpeedDurabilityLevel.getText();

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(SAVE_BUTTON_CONFIGURATION));
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        navigateToConfigurePageFromDashboard();

        Assert.assertTrue(selectedValue.contains(selectedOptionForCheck));
    }

    @Test
    public void testSetNumberOfBuildsThrottleBuilds() {
        final String messageDay = "Approximately 24 hours between builds";

        createPipeline();
        navigateToConfigurePageFromDashboard();
        scrollCheckBoxThrottleBuildsIsVisible();

        getDriver().findElement(By.xpath("//label[text()='Throttle builds']")).click();
        WebElement selectThrottleBuilds = getDriver().findElement(By.xpath("//select[@class='jenkins-select__input select']"));
        Select simpleDropDown = new Select(selectThrottleBuilds);
        simpleDropDown.selectByValue("day");

        WebElement dayElement = getDriver().findElement(By.xpath("//div[@class='ok']"));
        getWait5().until(ExpectedConditions.visibilityOf(dayElement));

        Assert.assertEquals(dayElement.getText(), messageDay);
    }
}
