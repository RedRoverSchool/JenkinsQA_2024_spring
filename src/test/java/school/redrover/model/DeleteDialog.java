package school.redrover.model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class DeleteDialog extends BasePage {
    public DeleteDialog(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "dialog .jenkins-button--primary")
    private WebElement yesButton;


    public <T> T clickYes(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(yesButton)).click();
        return page;
    }

    public String getYesButtonColorDeletingViaSidebar() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--color');",
                yesButton);
    }
}
