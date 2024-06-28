package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class ProjectMovePage<T extends BaseProjectPage<T>> extends BasePage<T> {

    private final T returnPage;

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement selectDestination;

    public ProjectMovePage(WebDriver driver) {
        super(driver);
        this.returnPage = null;
    }

    public ProjectMovePage(WebDriver driver, T returnProjectPage) {
        super(driver);
        returnPage = returnProjectPage;
    }

    public T getReturnPage() {
        return returnPage;
    }

    @Step("Select the destination Folder to move the project")
    public ProjectMovePage<T> selectDestinationFolderFromList(String destination) {
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
    public <E> E clickMoveButtonWhenMovedViaDropdown(E returnProjectPage) {
        moveButton.click();

        return returnProjectPage;
    }
}
