package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class FolderProjectPage extends BaseProjectPage<FolderProjectPage> {

    @FindBy(css = ".empty-state-section")
    private WebElement emptyStateSection;

    @FindBy(css = "tr > td > .jenkins-table__link > span:first-child")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//a[.='New Item']")
    private WebElement newItemOnSidebar;

    @FindBy(css = "td [href*='job']:first-child")
    private WebElement nestedProjectName;

    @FindBy(css = "h2.h4")
    private WebElement messageFromEmptyFolder;

    @FindBy(css = "a.content-block__link")
    private WebElement createAJob;

    @FindBy(css = "[class*='dropdown'] [href$='move']")
    private WebElement dropdownMove;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement dropdownRename;

    @FindBy(css = "[class*='dropdown'] [href$='Delete']")
    private WebElement dropdownDelete;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public Boolean isFolderEmpty() {
        return emptyStateSection.isDisplayed();
    }

    @Step("Click 'New Item' on the sidebar")
    public CreateNewItemPage clickNewItemOnSidebar() {
        newItemOnSidebar.click();

        return new CreateNewItemPage(getDriver());
    }

    @Step("Get Item list inside folder")
    public List<String> getItemListInsideFolder() {
        return itemsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Get first item name inside Folder")
    public String getNestedProjectName() {
        return nestedProjectName.getText();
    }

    @Step("Checking the presence of item with specific name inside Folder")
    public boolean isItemExistsInsideFolder(String nameItem) {
        return getItemListInsideFolder().contains(nameItem);
    }

    @Step("Get message inside empty Folder")
    public String getMessageFromEmptyFolder() {
        return messageFromEmptyFolder.getText();
    }

    @Step("Get link text for create a job")
    public String getLinkTextForCreateJob() {
        return createAJob.getText();
    }

    @Step("Checking the presence of a link to create a job on the page")
    public Boolean isLinkForCreateJobDisplayed() {
        return createAJob.isDisplayed();
    }

    @Step("Click 'Move' on the project dropdown menu")
    public ProjectMovePage<?> clickMoveOnDropdown() {
        dropdownMove.click();

        return new ProjectMovePage<>(getDriver());
    }

    @Step("Click 'Rename' on the project dropdown menu")
    public ProjectRenamePage<?> clickRenameOnDropdown() {
        dropdownRename.click();

        return new ProjectRenamePage<>(getDriver());
    }

    @Step("Click 'Delete' on dropdown menu")
    public FolderProjectPage clickDeleteOnDropdown() {
        dropdownDelete.click();

        return this;
    }

    @Step("Click 'Yes' in confirming dialog")
    public FolderProjectPage clickYesToConfirmDelete() {
        getWait2().until(ExpectedConditions.visibilityOf(yesButton)).click();

        return this;
    }

}
