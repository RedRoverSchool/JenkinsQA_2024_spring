package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodesErrorPage extends BasePage {

    @FindBy(xpath = "//div/p")
    private WebElement errorMessage;

    public NodesErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessageText() {

        return errorMessage.getText();
    }
}
