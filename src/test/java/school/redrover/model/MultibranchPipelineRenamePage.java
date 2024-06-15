package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineRenamePage extends BasePage<MultibranchPipelineRenamePage> {

    @FindBy(name = "newName")
    private WebElement newNameInput;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public MultibranchPipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    @Step("Clear 'New Name' input field")
    public MultibranchPipelineRenamePage clearNewNameInput() {
        newNameInput.clear();

        return this;
    }

    @Step("Type Project name in 'New Name' input field")
    public MultibranchPipelineRenamePage setItemName(String newProjectName) {
        newNameInput.sendKeys(newProjectName);

        return this;
    }

    @Step("Click 'Rename'")
    public <T> T clickRename(T page) {
        getWait10().until(ExpectedConditions.elementToBeClickable(renameButton)).click();

        return page;
    }
}
