package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class MovePage<T extends BaseProjectPage<T>> extends BasePage<T> {

    private final T returnPage;

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement selectDestination;

    public MovePage(WebDriver driver) {
        super(driver);
        this.returnPage = null;
    }

    public MovePage(WebDriver driver, T returnPage) {
        super(driver);
        this.returnPage = returnPage;
    }

    public T getReturnPage() {
        return returnPage;
    }

    @Step("Select the destination Folder to move the project")
    public MovePage<T> selectDestinationFolderFromList(String destination) {
        new Select(selectDestination)
                .selectByValue("/" + destination);

        return this;
    }

    @Step("Click 'Move' button when moved via sidebar")
    public T clickMoveButtonWhenMovedViaSidebar() {
        moveButton.click();

        return getReturnPage();
    }

    @Step("Click 'Move' button when moved via breadcrumbs")
    public T clickMoveButtonWhenMovedViaBreadcrumbs() {
        moveButton.click();

        return getReturnPage();
    }

    @Step("Click 'Move' button when moved via project dropdown")
    public <E> E clickMoveButtonWhenMovedViaDropdown(E returnPage) {
        moveButton.click();

        return returnPage;
    }
}
