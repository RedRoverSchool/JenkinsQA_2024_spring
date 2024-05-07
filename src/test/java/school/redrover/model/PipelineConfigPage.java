package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

import javax.swing.*;
import java.time.Duration;

public class PipelineConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//label[@data-title='Disabled']")
    private WebElement toggleSwitchEnableDisable;


    @FindBy(xpath = "//label[text() = 'Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name = '_.numToKeepStr']")
    private WebElement numberBuildsToKeep;

    @FindBy(xpath = "//button[@data-section-id='pipeline']")
    private WebElement scrollToPiplineScript;

    @FindBy(xpath = "//div[@class = 'samples']//select")
    private WebElement samplePiplineScript;

    public PipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return new PipelinePage(getDriver());
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

    public PipelineConfigPage scrollToPiplineScript() {
        getWait5().until(ExpectedConditions.elementToBeClickable(scrollToPiplineScript)).click();

        return this;
    }

    public PipelineConfigPage selectSamplePiplineScript(String scriptName) {
        WebElement sampleScript = getWait5().until(ExpectedConditions.visibilityOf(samplePiplineScript));
        Select sampleScriptSelect = new Select(sampleScript);
        sampleScriptSelect.selectByValue(scriptName);

        return this;
    }

}
