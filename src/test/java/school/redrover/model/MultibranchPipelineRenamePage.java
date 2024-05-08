package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import school.redrover.model.base.BasePage;

public class MultibranchPipelineRenamePage extends BasePage {

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement newNameInput;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public MultibranchPipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineStatusPage changeNameTo(String newName) {
        newNameInput.clear();
        newNameInput.sendKeys(newName);
        renameButton.click();

        return new MultibranchPipelineStatusPage(getDriver());
    }
}
