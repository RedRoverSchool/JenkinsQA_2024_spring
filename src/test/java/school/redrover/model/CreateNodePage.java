package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNodePage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(css = "[class$=radio__label]")
    private WebElement permanentAgentRadioButton;

    @FindBy(id = "ok")
    private WebElement okButton;

    public CreateNodePage(WebDriver driver) {
        super(driver);
    }

    public CreateNodePage setNodeName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public CreateNodePage selectPermanentAgentRadioButton() {
        permanentAgentRadioButton.click();

        return this;
    }

    public NodesCreationConfigurePage clickOkButton() {
        okButton.click();

        return new NodesCreationConfigurePage(getDriver());
    }
}
