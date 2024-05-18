package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class RenameDialogPage extends BasePage {

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement inputNewNameField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public RenameDialogPage(WebDriver driver) {
        super(driver);
    }

    public RenameDialogPage setNewItemName(String newItemName) {
        inputNewNameField.clear();
        inputNewNameField.sendKeys(newItemName);

        return new RenameDialogPage(getDriver());
    }

    public <T> T clickRename(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(renameButton)).click();

        return page;
    }
}