package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateViewPage extends BasePage<CreateViewPage> {

    @FindBy(id = "name")
    private WebElement viewNameInputField;

    @FindBy(css = "[for$='ListView']")
    private WebElement listViewRadioButton;

    @FindBy(id = "ok")
    private WebElement createButton;

    @FindBy (xpath = "//label[text() = 'My View']")
    private WebElement myViewRadioButton;

    public CreateViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Type View name {viewName} in the name input field")
    public CreateViewPage typeViewName(String viewName) {
        viewNameInputField.sendKeys(viewName);

        return this;
    }

    @Step("Click on the 'List View' radio button")
    public CreateViewPage clickListViewRadioButton() {
        listViewRadioButton.click();

        return this;
    }

    @Step("Click on the 'My View' radio button ")
    public CreateViewPage clickMyViewRadioButton() {
        myViewRadioButton.click();

        return this;
    }

    @Step("Click on the 'Create' button to confirm creating of 'List View'")
    public ListViewConfigPage clickCreateButtonForListView() {
        createButton.click();

        return new ListViewConfigPage(getDriver());
    }

    @Step("Click on the 'Create' button to confirm creating of 'My View'")
    public ViewPage clickCreateButtonForMyView() {
        createButton.click();

        return new ViewPage(getDriver());
    }
}
