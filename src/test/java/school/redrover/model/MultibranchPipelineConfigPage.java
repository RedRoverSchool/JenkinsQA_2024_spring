package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import school.redrover.model.base.BaseConfigPage;

public class MultibranchPipelineConfigPage extends
        BaseConfigPage<MultibranchPipelineProjectPage, MultibranchPipelineConfigPage> {

    @FindBy(css = "[data-title*='Disabled']")
    private WebElement statusToggle;

    @FindBy(id = "enable-disable-project")
    private WebElement toggleInput;

    @FindBy(id = "itemname-required")
    private WebElement errorRequiresName;

    @FindBy(className = "tippy-box")
    private WebElement toggleTooltip;

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver, new MultibranchPipelineProjectPage(driver));
    }

    @Step("Click the toggle to disable the project")
    public MultibranchPipelineConfigPage clickToggleToDisable() {
        statusToggle.click();

        return this;
    }

    @Step("Hover over the Toggle")
    public MultibranchPipelineConfigPage hoverOverToggle() {
        new Actions(getDriver()).moveToElement(statusToggle).perform();

        return this;
    }

    public String getTooltipText() {
        return toggleTooltip.getText();
    }

    public boolean isTooltipDisplayed() {
        return getWait2().until(ExpectedConditions.visibilityOf(toggleTooltip)).isDisplayed();
    }

    public String getStatusToggle() {
        return toggleInput.getDomProperty("checked");
    }

    public String getErrorRequiresName() {
        return errorRequiresName.getText();
    }
}
