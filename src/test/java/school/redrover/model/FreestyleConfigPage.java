package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseConfigPage;

import java.util.ArrayList;
import java.util.List;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleProjectPage> {

    @FindBy(css = "#side-panel h1")
    private WebElement headerSidePanel;

    @FindBy (xpath = "//*[@name='description']")
    private WebElement descriptionField;

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

    @FindBy(xpath = "//a[contains(@tooltip,'Help for feature:')]")
    private List<WebElement> tooltipList;

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

    public FreestyleProjectPage clickSaveButton() {
        saveButton.click();

        return new FreestyleProjectPage(getDriver());
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

    public List<String> getTooltipList() {
        List<String> result = new ArrayList<>();
        for (WebElement w : tooltipList) {

            if (w.isDisplayed()) {
                ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView({block:'center'});", w);
                new Actions(getDriver()).pause(500).moveToElement(w).pause(400).perform();
                result.add(getDriver().findElement(By.xpath("//div[@class='tippy-box']")).getText());
            }
        }
        return result;
    }
}
