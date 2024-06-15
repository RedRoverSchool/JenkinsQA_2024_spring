package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
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

    @FindBy(xpath = "//div[text()='Number of seconds']")
    private WebElement numberOfSecondsHint;

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

    public PipelineConfigPage(WebDriver driver) {
        super(driver, new PipelineProjectPage(driver));
    }

    public PipelineConfigPage addDescription(String descriptionText) {
        getWait2().until(ExpectedConditions.visibilityOf(descriptionTextArea)).sendKeys(descriptionText);

        return this;
    }

    public PipelineConfigPage clickAdvancedProjectOptionsMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(advancedProjectOptionsMenu)).click();

        return this;
    }

    public PipelineConfigPage clickAdvancedButton() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);

        return this;
    }

    public List<String> getSectionsNameList() {

        return sectionsNameList.stream()
                .map(WebElement::getText)
                .toList();
    }

    public PipelineConfigPage scrollToQuietPeriodCheckbox() {
        new Actions(getDriver())
                .scrollToElement(quietPeriodCheckbox)
                .scrollByAmount(0, 150)
                .perform();

        return this;
    }

    public PipelineConfigPage clickQuietPeriodCheckbox() {
        quietPeriodCheckbox.click();

        return this;
    }

    public PipelineConfigPage clickPipelineSpeedDurabilityOverrideCheckbox() {
        getWait5().until(ExpectedConditions.elementToBeClickable(pipelineSpeedDurabilityOverrideCheckbox)).click();

        return this;
    }

    public PipelineConfigPage setNumberOfSecondsInQuietPeriodInputField(int seconds) {
        quietPeriodInputField.clear();
        quietPeriodInputField.sendKeys(String.valueOf(seconds));

        return this;
    }

    public PipelineConfigPage setNumberOfSecondsInQuietPeriodInputField(double seconds) {
        quietPeriodInputField.clear();
        quietPeriodInputField.sendKeys(String.valueOf(seconds));

        return this;
    }

    public PipelineConfigPage clickNumberOfSecondsHint() {
        numberOfSecondsHint.click();

        return this;
    }

    public String getQuietPeriodInputFieldValue() {
        return getWait5().until(ExpectedConditions.visibilityOf(quietPeriodInputField)).getAttribute("value");

    }

    public String getQuietPeriodInputErrorText() {
        return getWait5().until(ExpectedConditions.visibilityOf(errorMessageForQuietPeriodInputField)).getText();
    }

    public PipelineConfigPage selectCustomPipelineSpeedDurabilityLevel(int index) {
        Select dropdown = new Select(customPipelineSpeedDurabilityLevelInput);
        dropdown.selectByIndex(index);

        return this;
    }

    public String getCustomPipelineSpeedDurabilityLevelText() {
        Select dropDown = new Select(customPipelineSpeedDurabilityLevelInput);
        return dropDown.getFirstSelectedOption().getText();
    }


    public PipelineConfigPage setDisplayNameDescription(String name) {
        getWait5().until(ExpectedConditions.elementToBeClickable(displayNameTextField)).sendKeys(name);

        return this;
    }

    public PipelineConfigPage clearDisplayNameDescription() {
        getWait10().until(ExpectedConditions.elementToBeClickable(displayNameTextField)).clear();

        return this;
    }

    public PipelineConfigPage clickDiscardOldBuilds() {
        getWait5().until(ExpectedConditions.elementToBeClickable(discardOldBuildsCheckbox)).click();

        return this;
    }

    public PipelineConfigPage setNumberBuildsToKeep(int numberOfBuilds) {
        WheelInput.ScrollOrigin scrollFromDuildField = WheelInput.ScrollOrigin.fromElement(numberBuildsToKeep);
        new Actions(getDriver())
                .scrollFromOrigin(scrollFromDuildField, 0, 80)
                .pause(Duration.ofMillis(200))
                .perform();
        getWait5().until(ExpectedConditions.visibilityOf(numberBuildsToKeep)).sendKeys(String.valueOf(numberOfBuilds));

        return this;
    }

    public PipelineConfigPage scrollToPipelineScript() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scrollToPipelineScript)).click();

        return this;
    }

    public PipelineConfigPage selectSamplePipelineScript(String scriptName) {
        WebElement sampleScript = getWait5().until(ExpectedConditions.visibilityOf(samplePipelineScript));
        Select sampleScriptSelect = new Select(sampleScript);
        sampleScriptSelect.selectByValue(scriptName);

        return this;
    }

    public PipelineConfigPage sendScript(int stagesQtt, String pipelineScript) {
        getDriver().findElement(By.className("ace_text-input")).sendKeys(pipelineScript);

        for (int i = 1; i <= stagesQtt; i++) {

            String stage = "\nstage('stage " + i + "') {\nsteps {\necho 'test " + i + "'\n";
            getDriver().findElement(By.className("ace_text-input")).sendKeys(stage, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
        }

        return this;
    }

    public PipelineConfigPage selectDropdownDefinition(Integer index) {
        WebElement dropDownDefinition = getDriver().findElement(By.xpath(
                "//section[@class = 'jenkins-section']//select[@class = 'jenkins-select__input dropdownList']/option[" + index + "]"));

        getWait5().until(ExpectedConditions.visibilityOf(dropDownDefinition)).click();
        return this;
    }

    public boolean isPipelineDisplayed() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='pipeline']"))).isDisplayed();
    }

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


    public PipelineConfigPage clickOnUseGroovySandboxCheckbox() {
        getWait5().until(ExpectedConditions.visibilityOf(useGroovySandboxCheckbox)).click();

        return new PipelineConfigPage(getDriver());
    }

    public boolean isScriptApprovalLinkShown() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(scriptApprovalLink)).isDisplayed();
    }
}
