package school.redrover.model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage {

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description>div:first-child")
    private WebElement description;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewButton;

    @FindBy(className = "textarea-preview")
    private WebElement previewTextArea;

    @FindBy(css = "#disable-project button")
    private WebElement disableProjectButton;

    @FindBy(css = "#breadcrumbBar li:last-child")
    private WebElement breadcrumbs;

    @FindBy(xpath = "//*[span = 'Delete Multi-configuration project']")
    private WebElement menuDelete;

    @FindBy(xpath = "//*[contains(@href, 'rename')]")
    private WebElement menuRename;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectPage clickAddDescriptionButton() {
        addDescriptionButton.click();

        return this;
    }

    public MultiConfigurationProjectPage addOrEditDescription(String description) {
        descriptionField.sendKeys(description);

        return this;
    }

    public MultiConfigurationProjectPage clearDescription() {
        descriptionField.clear();
        return this;
    }

    public MultiConfigurationProjectPage clickSaveDescription() {
        saveButton.click();

        return this;
    }

    public String getDescriptionText() {

        return getWait2().until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public MultiConfigurationConfigPage clickConfigureButton() {
        configureButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }

    public MultiConfigurationProjectPage clickPreview() {
        previewButton.click();

        return this;
    }

    public String getPreviewText() {

        return previewTextArea.getText();
    }

    public MultiConfigurationProjectPage clickDisableProject() {
        disableProjectButton.click();

        return this;
    }

    public boolean isProjectInsideFolder(String projectName, String folderName) {
        return breadcrumbs.getAttribute("data-href").contains(folderName + "/job/" + projectName);
    }

    public DeleteDialog clickDeleteInMenu(DeleteDialog dialog) {
        menuDelete.click();
        return dialog;
    }

    public MultiConfigurationConfirmRenamePage clickRenameInMenu() {
        menuRename.click();
        return new MultiConfigurationConfirmRenamePage(getDriver());
    }

    public boolean isDescriptionEmpty() {
        return getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#description>div")));
    }
}