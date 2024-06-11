package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;


public class ItemErrorPage extends BasePage<ItemErrorPage> {

    @FindBy(css = "#main-panel p")
    private WebElement messageText;

    @FindBy(xpath = "//h2")
    private WebElement errorText;

    public ItemErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getMessageText() {

        return getWait2().until(ExpectedConditions.visibilityOf(messageText)).getText();
    }

    public String getErrorText() {

        return getWait5().until(ExpectedConditions.visibilityOf(errorText)).getText();
    }
}
