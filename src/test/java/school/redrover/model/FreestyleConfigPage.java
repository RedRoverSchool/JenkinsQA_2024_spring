package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;

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

    @Step("Scroll page to 'Build Triggers Heading'")
    public FreestyleConfigPage scrollToBuildTriggersHeading() {
        scrollToElement(buildTriggersHeading);

        return this;
    }

    @Step("Click 'TriggerBuildsRemotely' Checkbox")
    public FreestyleConfigPage clickTriggerBuildsRemotelyCheckbox() {
        triggerBuildsRemotelyCheckbox.click();

        return this;
    }

    @Step("Type '{tokenName}' token name to 'Authentication token' input")
    public FreestyleConfigPage inputAuthenticationToken(String tokenName) {
        authenticationTokenInput.sendKeys(tokenName);

        return this;
    }

    @Step("Click 'Add timestamps to the Console Output' Checkbox")
    public FreestyleConfigPage clickAddTimestampsCheckbox() {
        super.scrollToElement(buildEnvironmentHeading);
        addTimestampsCheckbox.click();

        return this;
    }
}
