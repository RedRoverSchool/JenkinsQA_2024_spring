package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class RenameDialogPage extends BasePage<RenameDialogPage> {

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement inputNewNameField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public RenameDialogPage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear 'New Name' input field and type new name for the Project")
    public RenameDialogPage setNewItemName(String newItemName) {
        inputNewNameField.clear();
        inputNewNameField.sendKeys(newItemName);

        return new RenameDialogPage(getDriver());
    }

    @Step("Click 'Rename' button")
    public <T> T clickRename(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(renameButton)).click();

        return page;
    }
}