package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import school.redrover.model.base.BaseConfigPage;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineProjectPage,MultibranchPipelineConfigPage> {

    @FindBy(css = "[data-title*='Disabled']")
    private WebElement statusToggle;

    @FindBy(className = "tippy-box")
    private WebElement tooltip;

    @FindBy(id = "enable-disable-project")
    private WebElement toggleInput;

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver, new MultibranchPipelineProjectPage(driver));
    }

    public MultibranchPipelineConfigPage clickToggle() {
        statusToggle.click();
        return this;
    }

    public MultibranchPipelineConfigPage clickOnToggle() {
        statusToggle.click();
        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage hoverOverToggle() {
        getDriver().findElement(By.tagName("h1")).click();
        new Actions(getDriver()).moveToElement(statusToggle).perform();
        return this;
    }

    public String getTooltipText() {
        return tooltip.getText();
    }

    public boolean isTooltipDisplayed() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-box"))).isDisplayed();
    }

    public String getStatusToggle() {
        return toggleInput.getDomProperty("checked");
    }
}
