package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ConfirmRenamePage extends BasePage {

    @FindBy(xpath = "//*[text()='Error']")
    private WebElement errorMessage;

    public ConfirmRenamePage(WebDriver driver) {
        super(driver);
    }

    public Boolean isErrorMessageDisplayed() {

        return errorMessage.isDisplayed();
    }
}
