package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

public class DeleteDialog extends BasePage {
    public DeleteDialog(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "dialog .jenkins-button--primary")
    WebElement yesButton;


    public <T> T clickYes(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(yesButton)).click();
        return page;
    }
}
