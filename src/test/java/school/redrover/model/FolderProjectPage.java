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

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(xpath = "//span[contains(text(),'Rename')]/../.")
    private WebElement renameButtonLeft;

    @FindBy(css = "h1")
    private WebElement pageTopic;

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

    public FolderRenamePage clickOnRenameButtonLeft() {
        renameButtonLeft.click();

        return new FolderRenamePage(getDriver());
    }

    public String getPageHeading() {
        return pageHeading.getText();
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
}