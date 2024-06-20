package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNewViewPage extends BasePage<CreateNewViewPage> {

    @FindBy(id = "name")
    private WebElement viewNameText;

    @FindBy(css = "[for$='ListView']")
    private WebElement listViewRadioButton;

    @FindBy(id = "ok")
    private WebElement createButton;

    @FindBy (xpath = "//label[text() = 'My View']")
    private WebElement myViewRadioButton;

    public CreateNewViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Type new View name {viewName} in the view name field")
    public CreateNewViewPage setViewName(String viewName) {
        viewNameText.sendKeys(viewName);

        return this;
    }

    @Step("Click 'List View' radio button")
    public CreateNewViewPage clickListViewRadioButton() {
        listViewRadioButton.click();

        return this;
    }

    @Step("Click 'Create' button on 'CreteNewViewPage'")
    public ViewMyListConfigPage clickCreateViewButton() {
        createButton.click();

        return new ViewMyListConfigPage(getDriver());
    }

    @Step("Check 'My View' radio button ")
    public CreateNewViewPage clickMyViewRadioButton() {
        myViewRadioButton.click();

        return this;
    }

    @Step("Click 'Create' button to create My View")
    public ViewConfigPage clickCreateMyView() {
        createButton.click();

        return new ViewConfigPage(getDriver());
    }

    @Step("Click 'Create' button on 'CreteNewViewPage'")
    public ViewPage clickCreateButtonUponChoosingMyView() {
        createButton.click();

        return new ViewPage(getDriver());
    }
}
