package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodesCreationConfigurePage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public NodesCreationConfigurePage(WebDriver driver) {
        super(driver);
    }

    public NodesTablePage clickSaveButton() {
        saveButton.click();

        return new NodesTablePage(getDriver());
    }
}
