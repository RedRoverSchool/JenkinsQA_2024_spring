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

    @FindBy (xpath = "//label[text() = 'My View']")
    WebElement myViewRadioButton;

    @FindBy (xpath = "//div[@class='tab active']")
    WebElement newViewName;

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

    public CreateNewViewPage clickMyViewRadioButton() {
        myViewRadioButton.click();

        return this;
    }

    public ViewConfigPage clickCreateMyView() {
        createButton.click();

        return new ViewConfigPage(getDriver());
    }
}
