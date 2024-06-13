package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;


public class ItemErrorPage extends BasePage<ItemErrorPage> {

    @FindBy(css = "#main-panel p")
    private WebElement underErrorText;

    @FindBy(xpath = "//h2")
    private WebElement underOopsProblemText;

    public ItemErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {

        return getWait2().until(ExpectedConditions.visibilityOf(underErrorText)).getText();
    }

    public String getProblemText() {

        return getWait5().until(ExpectedConditions.visibilityOf(underOopsProblemText)).getText();
    }
}
