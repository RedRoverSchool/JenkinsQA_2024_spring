package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNodePage extends BasePage<CreateNodePage> {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(css = "[class$=radio__label]")
    private WebElement permanentAgentRadioButton;

    @FindBy(id = "ok")
    private WebElement createButton;

    public CreateNodePage(WebDriver driver) {
        super(driver);
    }

    @Step("Type the '{name}' text to name input field")
    public CreateNodePage typeNodeName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    @Step("Select radio button 'Permanent Agent'")
    public CreateNodePage selectPermanentAgentRadioButton() {
        permanentAgentRadioButton.click();

        return this;
    }

    @Step("Click on the button 'Create'")
    public NodesCreationConfigurePage clickCreateButton() {
        createButton.click();

        return new NodesCreationConfigurePage(getDriver());
    }

    @Step("Click on the button 'Create'")
    public NodesErrorPage clickCreateButtonOnError() {
        createButton.click();

        return new NodesErrorPage(getDriver());
    }
}
