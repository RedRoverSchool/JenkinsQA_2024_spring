package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class FreestyleConfigPage extends BasePage {

    @FindBy(id = "build-triggers")
    private WebElement buildTriggersHeading;

    @FindBy(css = "span:has(input[name='pseudoRemoteTrigger'])")
    private WebElement triggerBuildsRemotelyCheckbox;

    @FindBy(name = "authToken")
    private WebElement authenticationTokenIInput;

    @FindBy(id = "build-environment")
    private WebElement buildEnvironmentHeading;

    @FindBy(css = "span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper'])")
    private WebElement addTimestampsCheckbox;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestylePage clickSaveButton() {
        saveButton.click();

        return new FreestylePage(getDriver());
    }

    protected FreestyleConfigPage scrollToBuildTriggersHeading() {
        super.scrollToElement(buildTriggersHeading);

        return this;
    }

    protected FreestyleConfigPage clickTriggerBuildsRemotelyCheckbox() {
        triggerBuildsRemotelyCheckbox.click();

        return this;
    }

    protected FreestyleConfigPage inputAuthenticationToken(String projectName) {
        getWait2().until(ExpectedConditions.visibilityOf(authenticationTokenIInput))
                .sendKeys(projectName);

        return this;
    }

    protected FreestyleConfigPage clickAddTimestampsCheckbox() {
        super.scrollToElement(buildEnvironmentHeading);
        addTimestampsCheckbox.click();

        return this;
    }
}
