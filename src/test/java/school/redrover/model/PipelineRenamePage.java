package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement newNameInput;

    @FindBy(name = "Submit")
    private WebElement saveRenameButton;

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public PipelineRenamePage clearNameInputField() {
        newNameInput.clear();

        return this;
    }

    public PipelineRenamePage setNewName(String name) {
        newNameInput.sendKeys(name);

        return this;
    }

    public PipelineProjectPage clickSaveRenameButton() {
        saveRenameButton.click();

        return new PipelineProjectPage(getDriver());
    }
}
