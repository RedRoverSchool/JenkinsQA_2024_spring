package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class OrganizationFolderProjectPage extends BaseProjectPage<OrganizationFolderProjectPage> {

    @FindBy(css = ".task [href$='configure']")
    private WebElement configureButton;

    @FindBy(css = "h1 > svg")
    private WebElement itemIcon;

    @FindBy(xpath = "//a[contains(@href,'pipeline-syntax')]")
    private WebElement pipelineSyntaxButton;

    @FindBy(xpath = "//a[contains(.,'Rename')]")
    private WebElement renameButton;

    @FindBy(xpath = "//*[@id='description-link']")
    private WebElement descriptionLink;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement textareaDescription;

    @FindBy(xpath = "//*[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@id='description']/div")
    private WebElement description;

    @FindBy(xpath = "//a[@data-title='Delete Organization Folder']")
    private WebElement deleteOnSidebar;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButtonOnDeleteOrganizationFolderAlert;

    @FindBy(xpath = "//a[contains(@href,'console')]")
    private WebElement scanButton;

    @FindBy(xpath = "//h1")
    private WebElement scanText;

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Configure' on sidebar menu")
    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }

    @Step("Click on 'Pipeline Syntax' in the sidebar menu")
    public PipelineSyntaxPage clickSidebarPipelineSyntax() {
        pipelineSyntaxButton.click();

        return new PipelineSyntaxPage(getDriver());
    }

    @Step("Click 'Rename' on sidebar menu")
    public OrganizationFolderRenamePage clickSidebarRename() {
        renameButton.click();

        return new OrganizationFolderRenamePage(getDriver());
    }

    @Step("Click 'Add description'")
    public OrganizationFolderProjectPage clickAddDescription() {
        descriptionLink.click();
        return this;
    }

    @Step("Type description text into description input field")
    public OrganizationFolderProjectPage setDescription(String text) {
        textareaDescription.sendKeys(text);
        return this;
    }

    @Step("Click 'Save' button")
    public OrganizationFolderProjectPage clickSaveButton() {
        saveButton.click();
        return this;
    }

    public String getDescriptionText() {
        return description.getText();
    }

    @Step("Click 'Delete Organization Folder' on sidebar menu")
    public OrganizationFolderProjectPage clickSidebarDelete() {
        deleteOnSidebar.click();
        return this;
    }

    @Step("Click 'Scan Organization Folder Log' on the sidebar menu")
    public OrganizationFolderProjectPage clickSidebarScanOrganizationFolderLog() {
        scanButton.click();
        return this;
    }

    public String getScanText() {
        return scanText.getText();
    }

    @Step("Click 'Yes' button in the confirming deletion dialog")
    public HomePage clickYesForDeleteOrganizationFolder() {
        yesButtonOnDeleteOrganizationFolderAlert.click();
        return new HomePage(getDriver());
    }
}