package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import school.redrover.model.base.BasePage;

public class MultibranchPipelineConfigPage extends BasePage {

    @FindBy(css = "[data-title*='Disabled']")
    private WebElement statusToggle;

    @FindBy(className = "tippy-box")
    private WebElement tooltip;

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage clickToggle() {
        statusToggle.click();
        return this;
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
}
