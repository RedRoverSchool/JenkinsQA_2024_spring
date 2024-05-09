package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultiConfigurationConfirmRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement renameText;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public MultiConfigurationConfirmRenamePage(WebDriver driver) { super(driver); }

    public MultiConfigurationConfirmRenamePage changeProjectName(String text) {
        renameText.clear();
        renameText.sendKeys(text);

        return this;
    }

    public MultiConfigurationPage clickRenameButton() {
        renameButton.click();

        return new MultiConfigurationPage(getDriver());
    }
}
