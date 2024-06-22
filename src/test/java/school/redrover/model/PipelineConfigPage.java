package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseConfigPage;

import java.time.Duration;
import java.util.List;

public class PipelineConfigPage extends BaseConfigPage<PipelineProjectPage, PipelineConfigPage> {

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//label[text() = 'Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name = '_.numToKeepStr']")
    private WebElement numberBuildsToKeep;

    @FindBy(xpath = "//button[@data-section-id='pipeline']")
    private WebElement scrollToPipelineScript;

    @FindBy(xpath = "//div[@class = 'samples']//select")
    private WebElement samplePipelineScript;

    @FindBy(xpath = "//a[@previewendpoint='/markupFormatter/previewDescription']")
    private WebElement preview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement textareaPreview;

    @FindBy(xpath = "(//span[@class='ace_string'])[2]")
    private WebElement echoScript;

    @FindBy(xpath = "//button[@data-section-id='advanced-project-options']")
    private WebElement advancedProjectOptionsMenu;

    @FindBy(xpath = "//section[@class='jenkins-section']//button[@type='button']")
    private WebElement advancedButton;

    @FindBy(xpath = "//div[@class='setting-main']//input[contains(@checkurl, 'checkDisplayName')]")
    private WebElement displayNameTextField;

    @FindBy(xpath = "//label[text()='Quiet period']")
    private WebElement quietPeriodCheckbox;

    @FindBy(name = "quiet_period")
    private WebElement quietPeriodInputField;

    @FindBy(xpath = "//div[@class='form-container tr']//div[@class='error']")
    private WebElement errorMessageForQuietPeriodInputField;

    @FindBy(xpath = "//label[text()='Pipeline speed/durability override']")
    private WebElement pipelineSpeedDurabilityOverrideCheckbox;

    @FindBy(xpath = "//select[@class='setting-input']")
    private WebElement customPipelineSpeedDurabilityLevelInput;

    @FindBy(css = "#tasks > div")
    private List<WebElement> sectionsNameList;

    @FindBy(xpath = "//label[text()='Throttle builds']")
    private WebElement throttleBuilds;

    @FindBy(css = ".jenkins-select__input.select")
    private WebElement timePeriodSelect;

    @FindBy(css = ".ok")
    private WebElement messageAboutTimeBetweenBuilds;

    @FindBy(css = "[checkdependson='durationName']")
    private WebElement numberOfBuildsInThrottleBuilds;


    @FindBy(xpath = "//label[text()='Use Groovy Sandbox']")
    private WebElement useGroovySandboxCheckbox;

    @FindBy(linkText = "Script Approval Configuration")
    private WebElement scriptApprovalLink;

    @FindBy(className = "ace_text-input")
    private WebElement scriptInput;

    @FindBy(xpath = "//a[@href='api/']")
    private WebElement restAPI;

    public PipelineConfigPage(WebDriver driver) {
        super(driver, new PipelineProjectPage(driver));
    }

    @Step("Type {description} to Description input field")
    public PipelineConfigPage addDescription(String description) {
        getWait2().until(ExpectedConditions.visibilityOf(descriptionTextArea)).sendKeys(description);

        return this;
    }

    @Step("Click 'Advanced Project Options' on Sidebar")
    public PipelineConfigPage clickAdvancedProjectOptionsOnSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(advancedProjectOptionsMenu)).click();

        return this;
    }

    @Step("Click 'Advanced' under 'Advanced Project Options")
    public PipelineConfigPage clickAdvancedButton() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);

        return this;
    }

    private WebElement getTooltipLocator(String tooltipText) {
        return getDriver().findElement(By.cssSelector("a[tooltip='Help for feature: " + tooltipText + "']"));
    }

    public boolean isTooltipDisplayed(String tooltipText) {
        WebElement tooltip = getTooltipLocator(tooltipText);
        hoverOverElement(tooltip);

        return tooltip.isDisplayed();
    }

    public List<String> getSectionsNameList() {

        return sectionsNameList.stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Scroll to 'Quiet period' checkbox")
    public PipelineConfigPage scrollToQuietPeriodCheckbox() {
        new Actions(getDriver())
                .scrollToElement(quietPeriodCheckbox)
                .scrollByAmount(0, 150)
                .perform();

        return this;
    }

    @Step("Click 'Quiet period' checkbox")
    public PipelineConfigPage clickQuietPeriodCheckbox() {
        quietPeriodCheckbox.click();

        return this;
    }

    @Step("Click 'Pipeline speed/durability override' checkbox")
    public PipelineConfigPage clickPipelineSpeedDurabilityOverrideCheckbox() {
        getWait5().until(ExpectedConditions.elementToBeClickable(pipelineSpeedDurabilityOverrideCheckbox)).click();

        return this;
    }

    @Step("Clear 'Number of seconds' input field and type type quantity of seconds")
    public PipelineConfigPage setNumberOfSecondsInQuietPeriodInputField(int seconds) {
        quietPeriodInputField.clear();
        quietPeriodInputField.sendKeys(String.valueOf(seconds) + Keys.TAB);

        return this;
    }

    @Step("Clear 'Number of seconds' input field and type quantity of seconds")
    public PipelineConfigPage setNumberOfSecondsInQuietPeriodInputField(double seconds) {
        quietPeriodInputField.clear();
        quietPeriodInputField.sendKeys(String.valueOf(seconds) + Keys.TAB);

        return this;
    }

    public String getQuietPeriodInputFieldValue() {
        return getWait5().until(ExpectedConditions.visibilityOf(quietPeriodInputField)).getAttribute("value");

    }

    public String getQuietPeriodInputErrorText() {
        return getWait5().until(ExpectedConditions.visibilityOf(errorMessageForQuietPeriodInputField)).getText();
    }

    @Step("Select 'Custom Pipeline Speed/Durability Level' by index from dropdown menu")
    public PipelineConfigPage selectCustomPipelineSpeedDurabilityLevel(int index) {
        Select dropdown = new Select(customPipelineSpeedDurabilityLevelInput);
        dropdown.selectByIndex(index);

        return this;
    }

    public String getCustomPipelineSpeedDurabilityLevelText() {
        Select dropDown = new Select(customPipelineSpeedDurabilityLevelInput);
        return dropDown.getFirstSelectedOption().getText();
    }


    @Step("Type {name} in the 'Display Name' input field")
    public PipelineConfigPage setDisplayName(String name) {
        getWait5().until(ExpectedConditions.elementToBeClickable(displayNameTextField)).sendKeys(name);

        return this;
    }

    @Step("Clear 'Display Name' input field")
    public PipelineConfigPage clearDisplayName() {
        getWait10().until(ExpectedConditions.elementToBeClickable(displayNameTextField)).clear();

        return this;
    }

    @Step("Click 'Discard old builds' check box")
    public PipelineConfigPage clickDiscardOldBuilds() {
        getWait5().until(ExpectedConditions.elementToBeClickable(discardOldBuildsCheckbox)).click();

        return this;
    }

    @Step("Type quantity of builds to keep")
    public PipelineConfigPage setNumberOfBuildsToKeep(int numberOfBuilds) {
        WheelInput.ScrollOrigin scrollFromBuildField = WheelInput.ScrollOrigin.fromElement(numberBuildsToKeep);
        new Actions(getDriver())
                .scrollFromOrigin(scrollFromBuildField, 0, 80)
                .pause(Duration.ofMillis(200))
                .perform();
        getWait5().until(ExpectedConditions.visibilityOf(numberBuildsToKeep)).sendKeys(String.valueOf(numberOfBuilds));

        return this;
    }

    @Step("Click 'try sample Pipeline...' to open dropdown menu")
    public PipelineConfigPage clickTrySamplePipelineScript() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scrollToPipelineScript)).click();

        return this;
    }

    @Step("Scroll into the button of page")
    public PipelineConfigPage scrollIntoTheButtonOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement element = restAPI;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        return this;
    }

    @Step("Select {scriptName} option by value")
    public PipelineConfigPage selectSamplePipelineScript(String scriptName) {
        WebElement sampleScript = getWait5().until(ExpectedConditions.visibilityOf(samplePipelineScript));
        Select sampleScriptSelect = new Select(sampleScript);
        sampleScriptSelect.selectByValue(scriptName);

        return this;
    }

    @Step("Type {script} to 'Script' input field")
    public PipelineConfigPage sendScript(int stagesQuantity, String script) {
        scriptInput.sendKeys(script);

        for (int i = 1; i <= stagesQuantity; i++) {

            String stage = "\nstage('stage " + i + "') {\nsteps {\necho 'test " + i + "'\n";
            scriptInput.sendKeys(stage, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
        }

        return this;
    }

    @Step("Select from 'Definition' dropdown menu the script option")
    public PipelineConfigPage selectDropdownDefinition(Integer index) {
        WebElement dropDownDefinition = getDriver().findElement(By.xpath(
                "//section[@class = 'jenkins-section']"
                        + "//select[@class = 'jenkins-select__input dropdownList']/option[" + index + "]"));

        getWait5().until(ExpectedConditions.visibilityOf(dropDownDefinition)).click();
        return this;
    }

    @Step("Click 'Preview'")
    public PipelineConfigPage clickPreview() {
        preview.click();

        return new PipelineConfigPage(getDriver());
    }

    public String getTextareaPreviewText() {

        return textareaPreview.getText();
    }

    public String getScriptText() {

        return echoScript.getText();
    }

    @Step("Click on 'Throttle builds'")
    public PipelineConfigPage clickOnThrottleBuilds() {
        clickElementFromTheBottomOfThePage(throttleBuilds);

        return this;
    }

    @Step("Select Time period")
    public PipelineConfigPage selectTimePeriod(String timePeriod) {
        clickElementFromTheBottomOfThePage(timePeriodSelect);
        new Select(timePeriodSelect).selectByValue(timePeriod);

        return this;
    }

    @Step("Get message about time between builds")
    public String getMessageAboutTimeBetweenBuilds() {

        return getWait2().until(ExpectedConditions.visibilityOf(messageAboutTimeBetweenBuilds)).getText();
    }

    @Step("Clear input field 'Number of builds' in Throttle builds")
    public PipelineConfigPage clearNumberOfBuilds() {
        numberOfBuildsInThrottleBuilds.clear();

        return this;
    }

    @Step("Type text to input field 'Number of builds'")
    public PipelineConfigPage typeNumberOfBuilds(String numberOfBuilds) {
        numberOfBuildsInThrottleBuilds.sendKeys(numberOfBuilds);

        return this;
    }

    @Step("Click 'Use Groovy Sandbox' checkbox")
    public PipelineConfigPage clickOnUseGroovySandboxCheckbox() {
        scrollIntoViewCenter(useGroovySandboxCheckbox);
        getWait5().until(ExpectedConditions.elementToBeClickable(useGroovySandboxCheckbox)).click();

        return new PipelineConfigPage(getDriver());
    }

    public boolean isScriptApprovalLinkShown() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(scriptApprovalLink)).isDisplayed();
    }
}
