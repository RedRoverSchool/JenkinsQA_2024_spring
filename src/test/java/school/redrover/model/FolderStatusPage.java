package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "[href*='confirm-rename']")
    private WebElement renameButton;

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

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    public FolderRenamePage clickOnRenameButton() {
        renameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public String getPageTopic() {
        return pageTopic.getText();
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

    public FolderStatusPage clickAddOrEditDescription() {
        descriptionLink.click();
        return this;
    }

    public FolderStatusPage setDescription(String text) {
        textareaDescription.sendKeys(text);
        return this;
    }

    public FolderStatusPage clickSaveButton() {
        saveButton.click();
        return this;
    }

    public String getDescriptionText() {
        return description.getText();
    }

    public String getItemInTableName() {
        return itemInTable.getText();
    }

    public FolderStatusPage hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return this;
    }

    public FolderStatusPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    public MovePage clickDropdownMoveButton() {
        dropdownMoveButton.click();

        return new MovePage(getDriver());
    }

    public FolderStatusPage clickMainFolderName(String mainFolder) {
        getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job/" + mainFolder + "']")).click();

        return new FolderStatusPage(getDriver());
    }

    public String getNestedFolderName() {
        return nestedFolderName.getText();
    }
}
