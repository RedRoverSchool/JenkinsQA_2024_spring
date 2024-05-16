package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleRenamePage extends BasePage {

    @FindBy(xpath = "//input[@name='newName']")
    private WebElement textBox;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    public FreestyleRenamePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleRenamePage setNewName(String name) {
        textBox.clear();
        textBox.sendKeys(name);

        return this;
    }

    public FreestyleProjectPage clickRename() {

        submitButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    public ConfirmRenamePage clickRenameAnyway() {

        submitButton.click();

        return new ConfirmRenamePage(getDriver());
    }
}
