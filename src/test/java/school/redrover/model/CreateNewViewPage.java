package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNewViewPage extends BasePage {

    @FindBy(id = "name")
    WebElement viewNameText;

    @FindBy(css = "[for$='ListView']")
    WebElement listViewRadioButton;

    @FindBy(id = "ok")
    WebElement createButton;

    public CreateNewViewPage(WebDriver driver) { super(driver); }

    public CreateNewViewPage setViewName(String viewName) {
        viewNameText.sendKeys(viewName);

        return this;
    }

    public CreateNewViewPage clickListViewRadioButton() {
        listViewRadioButton.click();

        return this;
    }

    public ViewMyListConfigPage clickCreateView() {
        createButton.click();

        return new ViewMyListConfigPage(getDriver());
    }
}
