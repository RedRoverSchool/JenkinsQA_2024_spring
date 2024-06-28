package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class ProjectRenamePage<T extends BaseProjectPage<T>> extends BasePage<T> {

    private final T returnPage;

    @FindBy(name = "newName")
    private WebElement nameInputField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public ProjectRenamePage(WebDriver driver, T returnProjectPage) {
        super(driver);
        returnPage = returnProjectPage;
    }

    public ProjectRenamePage(WebDriver driver) {
        super(driver);
        this.returnPage = null;
    }

    public T getReturnPage() {
        return returnPage;
    }

    @Step("Clear old name in input field")
    public ProjectRenamePage<T> clearNameInputField() {
        nameInputField.clear();

        return this;
    }

    @Step("Type new name in input field on project rename page")
    public ProjectRenamePage<T> typeNewName(String name) {
        nameInputField.sendKeys(name);

        return this;
    }

    @Step("Click 'Rename' button for confirmation when renamed via sidebar")
    public T clickRenameButtonWhenRenamedViaSidebar() {
        renameButton.click();

        return getReturnPage();
    }

    @Step("Click 'Rename' button for confirmation when renamed via breadcrumbs")
    public T clickRenameButtonWhenRenamedViaBreadcrumbs() {
        renameButton.click();

        return getReturnPage();
    }


    @Step("Click 'Rename' button for confirmation when rename via project dropdown")
    public <E> E clickRenameButtonWhenRenamedViaDropdown(E renamedPage) {
        renameButton.click();

        return renamedPage;
    }
}
