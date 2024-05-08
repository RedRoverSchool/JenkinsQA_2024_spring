package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodePage extends BasePage {

    @FindBy(xpath = "//a[@href='editDescription']")
    private WebElement addDescription;

    @FindBy(xpath = "//div[@class='jenkins-!-margin-bottom-3']")
    private WebElement labels;

    @FindBy(id = "description")
    private WebElement descriptionArea;

    public NodePage(WebDriver driver) {
        super(driver);
    }

    public String getLabels() {
        return labels.getText();
    }

    public String getDescription(){
        return descriptionArea.getText();
    }
}
