package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineErrorPage extends BasePage<MultibranchPipelineErrorPage> {

    @FindBy(css = "#main-panel p")
    private WebElement errorText;

    public MultibranchPipelineErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {
        return getWait2().until(ExpectedConditions.visibilityOf(errorText)).getText();
    }
}
