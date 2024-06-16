package school.redrover.model;

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

    public ProjectRenamePage(WebDriver driver, T returnPage ) {
        super(driver);
        this.returnPage = returnPage;
    }

    public T getReturnPage() {
        return returnPage;
    }

    public ProjectRenamePage<T> clearNameInputField() {
        nameInputField.clear();

        return this;
    }

    public ProjectRenamePage<T> typeNewName(String name) {
        nameInputField.sendKeys(name);

        return this;
    }

    public T clickRenameButton() {
        renameButton.click();

        return getReturnPage();
    }
}