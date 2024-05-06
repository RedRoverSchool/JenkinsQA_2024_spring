package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

import javax.swing.*;

public class PipelineConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//label[text() = 'Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//label[text() = 'Discard old builds']")
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

    public PipelineConfigPage clickDiscardOldBuilds() {
        getWait5().until(ExpectedConditions.elementToBeClickable(discardOldBuildsCheckbox)).click();

        return this;
    }

    public PipelineConfigPage setNumberBuildsToKeep(int numberOfBuilds) {
        Actions action = new Actions(getDriver());
        action.moveToElement(numberBuildsToKeep, 0, 100)
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
