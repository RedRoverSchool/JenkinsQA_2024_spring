package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class RenameItemPage extends BasePage {

    @FindBy(name = "newName")
    private WebElement folderNameTextField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public RenameItemPage(WebDriver driver) {
        super(driver);
    }

    public FolderStatusPage setFolderNameAndSave (String newFolderName) {
        folderNameTextField.clear();
        folderNameTextField.sendKeys(newFolderName);
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }
}
