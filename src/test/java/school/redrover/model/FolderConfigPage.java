package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public FolderStatusPage clickSaveButton() {
        saveButton.click();

        return new FolderStatusPage(getDriver());
    }
}
