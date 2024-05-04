package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderRenamePage extends BasePage {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement textBox;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage setNewName(String name) {
        textBox.clear();
        textBox.sendKeys(name);

        return this;
    }

    public FolderStatusPage clickRename() {
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }
}
