package school.redrover.model;


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

    @FindBy(css = "button[formnovalidate*='NoValidate']")
    private WebElement enableProjectButton;

    @FindBy(css = "[id='enable-project']")
    private WebElement disableMessage;

    @FindBy(css = "#breadcrumbBar li:last-child")
    private WebElement breadcrumbs;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsProjectDropdownArrow;

    @FindBy(css = "button[href $= '/doDelete']")
    private WebElement breadcrumbsDropdownDelete;

    @FindBy(xpath = "//*[span = 'Delete Multi-configuration project']")
    private WebElement menuDelete;

    @FindBy(xpath = "//*[contains(@href, 'rename')]")
    private WebElement menuRename;

    @FindBy(xpath = "//*[contains(@href, 'move')]")
    private WebElement moveOptionInMenu;

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

    public MultiConfigurationProjectPage clickEnableButton() {
        enableProjectButton.click();

        return this;
    }

    public String getDisableMessage() {
        return disableMessage.getText();
    }

    public boolean isProjectInsideFolder(String projectName, String folderName) {
        return breadcrumbs.getAttribute("data-href").contains(folderName + "/job/" + projectName);
    }

    public DeleteDialog clickDeleteInMenu(DeleteDialog deleteDialog) {
        menuDelete.click();
        return new DeleteDialog(getDriver());
    }

    public MultiConfigurationRenamePage clickRenameInMenu() {
        menuRename.click();
        return new MultiConfigurationRenamePage(getDriver());
    }

    public boolean isDescriptionEmpty() {
        return getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#description>div")));
    }

    public MultiConfigurationMovePage clickMoveOptionInMenu() {
        moveOptionInMenu.click();
        return new MultiConfigurationMovePage(getDriver());
    }

    public MultiConfigurationProjectPage clickBreadcrumbsProjectDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsProjectDropdownArrow);

        return this;
    }

    public DeleteDialog clickDropdownDelete() {
        breadcrumbsDropdownDelete.click();
        return new DeleteDialog(getDriver());
    }
}