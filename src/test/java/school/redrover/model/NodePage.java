package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodePage extends BasePage<NodePage> {

    @FindBy(xpath = "//div[@class='jenkins-!-margin-bottom-3']")
    private WebElement labels;

    @FindBy(id = "description")
    private WebElement descriptionArea;

    @FindBy(css = "[data-title*='Delete']")
    private WebElement deleteAgent;

    @FindBy(css = "[data-id='ok']")
    private WebElement yesButton;

    public NodePage(WebDriver driver) {
        super(driver);
    }

    public String getLabels() {
        return labels.getText();
    }

    public String getDescription(){
        return descriptionArea.getText();
    }

    public NodePage clickDeleteAgent() {
        deleteAgent.click();

        return this;
    }

    public NodesTablePage clickYesButton() {
        yesButton.click();

        return new NodesTablePage(getDriver());
    }
}
