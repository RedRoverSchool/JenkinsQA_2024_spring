package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;

import java.util.ArrayList;
import java.util.List;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleProjectPage, FreestyleConfigPage> {

    @FindBy(css = "#side-panel h1")
    private WebElement headerSidePanel;

    @FindBy(xpath = "//*[@name='description']")
    private WebElement descriptionField;

    @FindBy(id = "build-triggers")
    private WebElement buildTriggersHeading;

    @FindBy(css = "span:has(input[name='pseudoRemoteTrigger'])")
    private WebElement triggerBuildsRemotelyCheckbox;

    @FindBy(name = "authToken")
    private WebElement authenticationTokenInput;

    @FindBy(id = "build-environment")
    private WebElement buildEnvironmentHeading;

    @FindBy(css = "span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper'])")
    private WebElement addTimestampsCheckbox;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver, new FreestyleProjectPage(driver));
    }

    public String getHeaderSidePanelText() {
        return headerSidePanel.getText();
    }

    public FreestyleConfigPage setDescription(String description) {
        descriptionField.sendKeys(description);

        return new FreestyleConfigPage(getDriver());
    }

    @Step("Click 'Save' button")
    public FreestyleProjectPage clickSaveButton() {
        saveButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage scrollToBuildTriggersHeading() {
        scrollToElement(buildTriggersHeading);

        return this;
    }

    public FreestyleConfigPage clickTriggerBuildsRemotelyCheckbox() {
        triggerBuildsRemotelyCheckbox.click();

        return this;
    }

    public FreestyleConfigPage inputAuthenticationToken(String projectName) {
        authenticationTokenInput.sendKeys(projectName);

        return this;
    }

    public FreestyleConfigPage clickAddTimestampsCheckbox() {
        super.scrollToElement(buildEnvironmentHeading);
        addTimestampsCheckbox.click();

        return this;
    }
}
