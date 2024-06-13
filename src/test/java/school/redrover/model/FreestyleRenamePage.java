package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleRenamePage extends BasePage<FreestyleRenamePage> {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement textBox;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    public FreestyleRenamePage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear 'New Name' input field and type new name for the Project")
    public FreestyleRenamePage setNewName(String name) {
        textBox.clear();
        textBox.sendKeys(name);

        return this;
    }

    public FreestyleProjectPage clickRename() {

        submitButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    @Step("Clear 'New Name' input field and type new name for the Project")
    public ItemErrorPage clearNameAndClickRenameButton() {
        textBox.clear();
        submitButton.click();

        return new ItemErrorPage(getDriver());
    }
}
