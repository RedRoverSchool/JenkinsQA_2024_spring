package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodesCreationConfigurePage extends BasePage<NodesCreationConfigurePage> {

    @FindBy(name = "Submit")
    private WebElement saveButton;
    @FindBy(name = "_.labelString")
    private WebElement labelsField;

    @FindBy(name = "nodeDescription")
    private WebElement descriptionField;

    public NodesCreationConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the button 'Save'")
    public NodesTablePage clickSaveButton() {
        saveButton.click();

        return new NodesTablePage(getDriver());
    }

    @Step("Type text to the Labels input field")
    public NodesCreationConfigurePage typeToLabelsInputField(String labelName) {
        labelsField.sendKeys(labelName);

        return new NodesCreationConfigurePage(getDriver());
    }

    @Step("Type text to the Description input area")
    public NodesCreationConfigurePage typeDescriptionText(String description) {
        descriptionField.sendKeys(description);

        return new NodesCreationConfigurePage(getDriver());
    }
}
