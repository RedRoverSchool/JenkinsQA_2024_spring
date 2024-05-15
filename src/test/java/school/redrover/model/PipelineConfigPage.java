package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseConfigPage;

import java.time.Duration;

public class PipelineConfigPage extends BaseConfigPage<PipelineProjectPage> {

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//label[@data-title='Disabled']")
    private WebElement toggleSwitchEnableDisable;

    @FindBy(xpath = "//label[text() = 'Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name = '_.numToKeepStr']")
    private WebElement numberBuildsToKeep;

    @FindBy(xpath = "//button[@data-section-id='pipeline']")
    private WebElement scrollToPipelineScript;

    @FindBy(xpath = "//div[@class = 'samples']//select")
    private WebElement samplePipelineScript;

    @FindBy(xpath = "//*[@id='pipeline]")
    private WebElement isPipelineDisplayed;

    @FindBy(xpath = "//a[@previewendpoint='/markupFormatter/previewDescription']")
    private WebElement preview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement textareaPreview;

    @FindBy(xpath = "(//span[@class='ace_string'])[2]")
    private WebElement echoScript;

    public PipelineConfigPage(WebDriver driver) {
        super(driver, new PipelineProjectPage(driver));
    }

    public PipelineConfigPage addDescription(String descriptionText) {
        getWait2().until(ExpectedConditions.visibilityOf(descriptionTextArea)).sendKeys(descriptionText);

        return this;
    }

    public PipelineConfigPage clickToggleSwitchEnableDisable() {
        getWait2().until(ExpectedConditions.visibilityOf(toggleSwitchEnableDisable)).click();

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

    public PipelineConfigPage sendScript(int stagesQtt) {
        String pipelineScript = "pipeline {\nagent any\n\nstages {\n";

        getDriver().findElement(By.className("ace_text-input")).sendKeys(pipelineScript);

        for (int i = 1; i <= stagesQtt; i++) {

            String stage = "\nstage(\'stage " + i + "\') {\nsteps {\necho \'test " + i + "\'\n";
            getDriver().findElement(By.className("ace_text-input")).sendKeys(stage, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
        }

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
}
