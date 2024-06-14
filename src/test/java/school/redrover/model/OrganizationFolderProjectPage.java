package school.redrover.model;

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

    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }

    public PipelineSyntaxPage clickSidebarPipelineSyntax() {
        pipelineSyntaxButton.click();

        return new PipelineSyntaxPage(getDriver());
    }


    public OrganizationFolderRenamePage clickSidebarRenameButton() {
        renameButton.click();

        return new OrganizationFolderRenamePage(getDriver());
    }

    public OrganizationFolderProjectPage clickAddDescription() {
        descriptionLink.click();
        return this;
    }

    public OrganizationFolderProjectPage setDescription(String text) {
        textareaDescription.sendKeys(text);
        return this;
    }

    public OrganizationFolderProjectPage clickSaveButton() {
        saveButton.click();
        return this;
    }

    public String getDescriptionText() {
        return description.getText();
    }

    public OrganizationFolderProjectPage clickDeleteOnSidebar() {
        deleteOnSidebar.click();
        return this;
    }

    public OrganizationFolderProjectPage clickSidebarScanOrganizationFolderLog() {
        scanButton.click();
        return this;
    }

    public String getScanText() {
        return scanText.getText();
    }

    public HomePage clickYesForDeleteOrganizationFolder() {
        yesButtonOnDeleteOrganizationFolderAlert.click();
        return new HomePage(getDriver());
    }
}
