package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;
import java.util.stream.Collectors;

public class FolderProjectPage extends BaseProjectPage<FolderProjectPage> {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "[href*='confirm-rename']")
    private WebElement renameButton;

    @FindBy(css = ".empty-state-section")
    private WebElement emptyStateSection;

    @FindBy(css = "tr > td > .jenkins-table__link > span:first-child")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//a[.='New Item']")
    private WebElement newItem;

    @FindBy(xpath = "//*[@id='description']/div")
    private WebElement description;

    @FindBy(xpath = "//*[@id='description-link']")
    private WebElement descriptionLink;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement textareaDescription;

    @FindBy(xpath = "//*[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//tr[contains(@id,'job_')]/td[3]/a")
    private WebElement itemInTable;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(css = "[class*='dropdown'] [href$='move']")
    private WebElement dropdownMoveButton;

    @FindBy(css = "td [href*='job']:first-child")
    private WebElement nestedProjectName;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement dropdownRenameButton;

    @FindBy(css = "a[data-title='Delete Folder']")
    private WebElement deleteOnSidebar;

    @FindBy(css = "button[data-id='ok']")
    private WebElement yesButtonOnDeleteFolderAlert;

    @FindBy(css = "h2.h4")
    private WebElement messageFromEmptyFolder;

    @FindBy(css = "a.content-block__link")
    private WebElement createJobLink;

    @FindBy(xpath = "//button[@class='jenkins-dropdown__item'][contains(@href, 'Delete')]")
    private WebElement deleteProject;

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    @Step("Click 'Rename' on sidebar menu")
    public FolderRenamePage clickSidebarRename() {
        renameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public Boolean isFolderEmpty() {
        return emptyStateSection.isDisplayed();
    }

    @Step("Click 'New Item' on sidebar menu")
    public CreateNewItemPage clickNewItemInsideFolder() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemListInsideFolder() {
        return itemsList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Click 'Add description' button")
    public FolderProjectPage clickAddDescription() {
        descriptionLink.click();
        return this;
    }

    @Step("Click 'Edit description' button")
    public FolderProjectPage clickEditDescription() {
        descriptionLink.click();
        return this;
    }

    @Step("Type description text in the input field")
    public FolderProjectPage setDescription(String text) {
        textareaDescription.sendKeys(text);
        return this;
    }

    @Step("Clear description input field")
    public FolderProjectPage clearDescription() {
        textareaDescription.clear();
        return this;
    }

    @Step("Click 'Save' button")
    public FolderProjectPage clickSaveButton() {
        saveButton.click();
        return this;
    }

    public String getDescriptionText() {
        return description.getText();
    }

    public String getItemInTableName() {
        return itemInTable.getText();
    }

    @Step("Hover over Breadcrumbs Specific Project name")
    public FolderProjectPage hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return this;
    }

    @Step("Click dropdown arrow for specific Project name")
    public FolderProjectPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    @Step("Click 'Move' on dropdown menu")
    public MovePage clickDropdownMoveButton() {
        dropdownMoveButton.click();

        return new MovePage(getDriver());
    }

    @Step("Click 'Main' folder name")
    public FolderProjectPage clickMainFolderName(String mainFolder) {
        getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job/" + mainFolder + "']")).click();

        return new FolderProjectPage(getDriver());
    }

    public String getNestedProjectName() {
        return nestedProjectName.getText();
    }

    @Step("Click 'Rename' from dropdown menu")
    public FolderRenamePage clickDropdownRenameButton() {
        dropdownRenameButton.click();

        return new FolderRenamePage(getDriver());
    }

    @Step("Click 'Delete' on sidebar menu")
    public FolderProjectPage clickDeleteOnSidebar() {
        deleteOnSidebar.click();

        return this;
    }

    @Step("Click 'Yes' button in confirming alert")
    public HomePage clickYesForDeleteFolder() {
        yesButtonOnDeleteFolderAlert.click();

        return new HomePage(getDriver());
    }

    public boolean isItemExistsInsideFolder(String nameItem) {
        return getItemListInsideFolder().contains(nameItem);
    }

    public String getMessageFromEmptyFolder() {
        return messageFromEmptyFolder.getText();
    }

    public String getTextWhereClickForCreateJob() {
        return createJobLink.getText();
    }

    public Boolean isLinkForCreateJobDisplayed() {
        return createJobLink.isDisplayed();
    }
}
