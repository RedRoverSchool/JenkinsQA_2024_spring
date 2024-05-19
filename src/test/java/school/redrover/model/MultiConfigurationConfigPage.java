package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseConfigPage;

import java.util.List;

public class MultiConfigurationConfigPage extends BaseConfigPage<MultiConfigurationProjectPage> {

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement toggleSwitch;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    @FindBy(id = "itemname-required")
    private WebElement errorRequiresName;

    @FindBy(xpath = "//label[text()='Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//*[@name='_.daysToKeepStr']")
    private WebElement daysToKeep;

    @FindBy(xpath = "//*[@name='_.numToKeepStr']")
    private WebElement maxNumberOfBuildsToKeep;

    @FindBy(css = "[name='strategy'] .advancedButton")
    private WebElement advancedButton;

    @FindBy(css = "[name*='artifactDaysToKeepStr']")
    private WebElement artifactDaysToKeepStr;

    @FindBy(css = "[name*='artifactNumToKeepStr']")
    private WebElement artifactNumToKeepStr;

    @FindBy(css = "[name='strategy'] input.positive-number")
    private List<WebElement> discardOldBuildsList;

    @FindBy(css = "[href*='job']")
    private WebElement breadcrumbsProjectName;

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver, new MultiConfigurationProjectPage(driver));
    }

    public MultiConfigurationConfigPage clickToggleSwitch() {
        toggleSwitch.click();

        return this;
    }

    public MultiConfigurationConfigPage clickApply() {
        applyButton.click();

        return this;
    }

    public boolean getStatusToggle() {
        return toggleSwitch.isSelected();
    }

    public String getToggleStatusMessage() {
        return toggleSwitch.getText();
    }

    public String getErrorRequiresName() {
        return errorRequiresName.getText();
    }

    public MultiConfigurationConfigPage clickDiscardOldBuilds() {
        discardOldBuildsCheckbox.click();

        return this;
    }

    public MultiConfigurationConfigPage setDaysToKeep(String days) {
        daysToKeep.sendKeys(days);

        return this;
    }

    public MultiConfigurationConfigPage setMaxNumberOfBuildsToKeep(String number) {
        maxNumberOfBuildsToKeep.sendKeys(number);

        return this;
    }

    public MultiConfigurationConfigPage clickAdvancedButton() {
        advancedButton.click();

        return this;
    }

    public MultiConfigurationConfigPage setArtifactDaysToKeepStr(String days) {
        artifactDaysToKeepStr.sendKeys(days);

        return this;
    }

    public MultiConfigurationConfigPage setArtifactNumToKeepStr(String number) {
        artifactNumToKeepStr.sendKeys(number);

        return this;
    }

    public List<String> getDiscardOldBuildsListText() {
        return discardOldBuildsList
                .stream()
                .map(e -> e.getAttribute("value"))
                .toList();
    }

    public MultiConfigurationProjectPage clickBreadcrumbsProjectName(String projectName) {
        breadcrumbsProjectName.click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationConfigPage hoverOverToggleSwitch() {
        new Actions(getDriver())
                .moveToElement(toggleSwitch)
                .perform();

        return this;
    }

    public String getToggleTooltipText() {

        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tippy-box>div"))).getText();
    }
}
