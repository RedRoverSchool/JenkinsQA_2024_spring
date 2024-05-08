package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodesCreationConfigurePage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;
    @FindBy(name = "_.labelString")
    private WebElement labelsField;

    @FindBy(name = "nodeDescription")
    private WebElement descriptionField;

    public NodesCreationConfigurePage(WebDriver driver) {
        super(driver);
    }

    public NodesTablePage clickSaveButton() {
        saveButton.click();

        return new NodesTablePage(getDriver());
    }

    public NodesCreationConfigurePage createLabel(String labelName) {
        labelsField.sendKeys(labelName);

        return new NodesCreationConfigurePage(getDriver());
    }

    public NodesCreationConfigurePage addDescription(String description) {
        descriptionField.sendKeys(description);

        return new NodesCreationConfigurePage(getDriver());
    }
}
