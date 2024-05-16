package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class FolderProjectPage extends BaseProjectPage {

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
    private WebElement nestedFolderName;

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

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    public FolderRenamePage clickOnRenameButton() {
        renameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public Boolean isFolderEmpty() {
        return emptyStateSection.isDisplayed();
    }

    public CreateNewItemPage clickNewItemInsideFolder() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemListInsideFolder() {
        return itemsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public FolderProjectPage clickAddOrEditDescription() {
        descriptionLink.click();
        return this;
    }

    public FolderProjectPage setDescription(String text) {
        textareaDescription.sendKeys(text);
        return this;
    }

    public FolderProjectPage clearDescription() {
        textareaDescription.clear();
        return this;
    }

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

    public FolderProjectPage hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return this;
    }

    public FolderProjectPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    public MovePage clickDropdownMoveButton() {
        dropdownMoveButton.click();

        return new MovePage(getDriver());
    }

    public FolderProjectPage clickMainFolderName(String mainFolder) {
        getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job/" + mainFolder + "']")).click();

        return new FolderProjectPage(getDriver());
    }

    public String getNestedFolderName() {
        return nestedFolderName.getText();
    }

    public FolderRenamePage clickDropdownRenameButton() {
        dropdownRenameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public FolderProjectPage clickDeleteOnSidebar() {
        deleteOnSidebar.click();

        return this;
    }

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
