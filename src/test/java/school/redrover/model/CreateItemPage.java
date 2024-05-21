package school.redrover.model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateItemPage extends BasePage {

    @FindBy(css = "#main-panel h1")
    private WebElement pageHeader;

    @FindBy(css = "#main-panel p")
    private WebElement errorMessage;

    public CreateItemPage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeaderText() {
        return pageHeader.getText();
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

}
