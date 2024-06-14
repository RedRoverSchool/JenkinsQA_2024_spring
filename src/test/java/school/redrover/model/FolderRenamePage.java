package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderRenamePage extends BasePage<FolderRenamePage> {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement textBox;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear the 'New Name' input field and type new Project Name")
    public FolderRenamePage setNewName(String name) {
        textBox.clear();
        textBox.sendKeys(name);

        return this;
    }

    @Step("Click 'Rename' button")
    public FolderProjectPage clickRename() {
        submitButton.click();

        return new FolderProjectPage(getDriver());
    }
}
