package school.redrover.model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class MultiConfigurationPage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description>div:first-child")
    private WebElement description;

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    public MultiConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationPage clickAddDescriptionButton() {
        addDescriptionButton.click();

        return this;
    }

    public MultiConfigurationPage addOrEditDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    public MultiConfigurationPage clickSaveDescription() {
        saveButton.click();

        return this;
    }

    public String getDescriptionText() {

        return getWait2().until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public String getProjectNameText() {

        return projectName.getText();
    }

    public MultiConfigurationConfigPage clickConfigureButton() {
        configureButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }
}