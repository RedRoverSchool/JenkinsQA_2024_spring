package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;


public class CreateItemErrorPage extends BasePage<CreateItemErrorPage> {

    @FindBy(css = "#main-panel p")
    private WebElement errorDescribingText;

    @FindBy(xpath = "//h2")
    private WebElement underOopsProblemText;

    public CreateItemErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {

        return getWait2().until(ExpectedConditions.visibilityOf(errorDescribingText)).getText();
    }

    public String getProblemText() {

        return getWait5().until(ExpectedConditions.visibilityOf(underOopsProblemText)).getText();
    }
}
