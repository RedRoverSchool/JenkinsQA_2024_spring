package school.redrover.model;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage<MultiConfigurationProjectPage> {

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewButton;

    @FindBy(className = "textarea-preview")
    private WebElement previewTextArea;

    @FindBy(css = "#disable-project button")
    private WebElement disableProjectButton;

    @FindBy(css = "button[formnovalidate*='NoValidate']")
    private WebElement enableProjectButton;

    @FindBy(css = "[id='enable-project']")
    private WebElement disableMessage;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsProjectDropdownArrow;

    @FindBy(xpath = "//*[contains(@href, 'move')]")
    private WebElement moveOptionInMenu;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click  on 'Add Description' Button")
    public MultiConfigurationProjectPage clickAddDescriptionButton() {
        addDescriptionButton.click();

        return this;
    }


    @Step("Type a description into the 'Description' input field")
    public MultiConfigurationProjectPage addOrEditDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    @Step("Clear 'Description' input field")
    public MultiConfigurationProjectPage clearDescription() {
        descriptionField.clear();
        return this;
    }

    @Step("Click on 'Save description' button")
    public MultiConfigurationProjectPage clickSaveDescription() {
        saveButton.click();

        return this;
    }

    @Step("Click on the button 'Configure' on sidebar")
    public MultiConfigurationConfigPage clickConfigureButton() {
        configureButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }

    @Step("Click on 'Preview' button ")
    public MultiConfigurationProjectPage clickPreview() {
        previewButton.click();

        return this;
    }

    public String getPreviewText() {

        return previewTextArea.getText();
    }

    @Step("Click on 'Disable' button")
    public MultiConfigurationProjectPage clickDisableProject() {
        disableProjectButton.click();

        return this;
    }

    @Step("Click on the 'Enable' button.")
    public MultiConfigurationProjectPage clickEnableButton() {
        enableProjectButton.click();

        return this;
    }

    public String getDisableMessage() {
        return disableMessage.getText();
    }

    public boolean isDescriptionEmpty() {
        return getWait10().until(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#description>div")));
    }

    public MultiConfigurationProjectPage clickBreadcrumbsProjectDropdownArrow() {
        clickBreadcrumbsDropdownArrow(breadcrumbsProjectDropdownArrow);

        return this;
    }
}
