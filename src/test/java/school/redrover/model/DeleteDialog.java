package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class DeleteDialog<T extends BasePage<T>> extends BasePage<T> {

    private T returnPage;

    @FindBy(css = "dialog .jenkins-button--primary")
    private WebElement yesButton;

    public DeleteDialog(WebDriver driver, T returnPage) {
        super(driver);
        this.returnPage = returnPage;
    }

    public T getReturnPage() {
        return returnPage;
    }

    @Step("Click 'Yes' button")
    public <T> T clickYes(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return page;
    }

    public T clickYesForConfirmDelete() {
        getWait5().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return getReturnPage();
    }
}
