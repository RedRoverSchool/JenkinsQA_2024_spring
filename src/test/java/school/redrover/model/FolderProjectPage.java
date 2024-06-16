package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

import java.util.List;

public class FolderProjectPage extends BaseProjectPage<FolderProjectPage> {

    @FindBy(css = ".empty-state-section")
    private WebElement emptyStateSection;

    @FindBy(css = "tr > td > .jenkins-table__link > span:first-child")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//a[.='New Item']")
    private WebElement newItemOnSidebar;

    @FindBy(xpath = "//tr[contains(@id,'job_')]/td[3]/a")
    private WebElement itemInTable;

    @FindBy(css = "td [href*='job']:first-child")
    private WebElement nestedProjectName;

    @FindBy(css = "h2.h4")
    private WebElement messageFromEmptyFolder;

    @FindBy(css = "a.content-block__link")
    private WebElement createAJob;

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

    @Step("Get Item name from dashboard")
    public String getItemNameFromDashboard() {
        return itemInTable.getText();
    }

    @Step("Click on the main folder name on breadcrumbs")
    public FolderProjectPage clickMainFolderName(String mainFolder) {
        getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job/" + mainFolder + "']")).click();

        return new FolderProjectPage(getDriver());
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
}
