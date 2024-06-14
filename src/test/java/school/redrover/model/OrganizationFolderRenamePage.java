package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderRenamePage extends BasePage<OrganizationFolderRenamePage> {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement textBox;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    public OrganizationFolderRenamePage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear 'New Name' input field and type new name")
    public OrganizationFolderRenamePage setNewName(String name) {
        textBox.clear();
        textBox.sendKeys(name);

        return this;
    }

    @Step("Click 'Rename' button")
    public OrganizationFolderProjectPage clickRename() {
        submitButton.click();

        return new OrganizationFolderProjectPage(getDriver());
    }
}
