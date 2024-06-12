package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultiConfigurationRenamePage extends BasePage<MultiConfigurationRenamePage> {

    @FindBy(name = "newName")
    private WebElement renameText;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public MultiConfigurationRenamePage(WebDriver driver) { super(driver); }

    public MultiConfigurationRenamePage changeProjectNameWithoutClear(String text) {
        renameText.sendKeys(text);

        return this;
    }

    public MultiConfigurationRenamePage changeProjectNameWithClear(String text) {
        renameText.clear();
        renameText.sendKeys(text);

        return this;
    }

    public MultiConfigurationProjectPage clickRenameButton() {
        renameButton.click();

        return new MultiConfigurationProjectPage(getDriver());
    }
}
