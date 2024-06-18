package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineRenamePage extends BasePage<PipelineRenamePage> {

    @FindBy(name = "newName")
    private WebElement newNameInput;

    @FindBy(name = "Submit")
    private WebElement saveRenameButton;

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear the input name field")
    public PipelineRenamePage clearNameInputField() {
        newNameInput.clear();

        return this;
    }

    @Step("Type {name} in the 'New Name' input field")
    public PipelineRenamePage setNewName(String name) {
        newNameInput.sendKeys(name);

        return this;
    }

    @Step("Click 'Rename' button to confirm renaming")
    public PipelineProjectPage clickRenameButton() {
        saveRenameButton.click();

        return new PipelineProjectPage(getDriver());
    }
}
