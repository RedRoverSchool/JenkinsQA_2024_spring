package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class NodesTablePage extends BasePage {

    @FindBy(css = "[href='new']")
    private WebElement newNodeButton;

    public NodesTablePage(WebDriver driver) {
        super(driver);
    }

    public CreateNodePage clickNewNodeButton() {
        newNodeButton.click();

        return new CreateNodePage(getDriver());
    }
}
