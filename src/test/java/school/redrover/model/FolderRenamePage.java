package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement folderNameTextField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage setNewName(String name) {
        folderNameTextField.clear();
        folderNameTextField.sendKeys(name);

        return this;
    }

    public FolderStatusPage clickSave() {
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }
}
