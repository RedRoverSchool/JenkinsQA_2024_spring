package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.HomePage;
import school.redrover.model.ProjectMovePage;
import school.redrover.model.ProjectRenamePage;
import school.redrover.model.RenameErrorPage;

import java.util.List;

public abstract class BaseProjectPage<T extends BaseProjectPage<T>> extends BasePage<T> {

    @FindBy(css = "dialog .jenkins-button--primary")
    private WebElement yesButton;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(css = "#description>:first-child")
    private WebElement displayedDescription;

    @FindBy(css = ".textarea-preview")
    private WebElement descriptionPreview;

    @FindBy(css = ".textarea-show-preview")
    private WebElement showDescriptionPreview;

    @FindBy(css = ".textarea-hide-preview")
    private WebElement hideDescriptionPreview;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]")
    private WebElement sidebarDelete;

    @FindBy(css = "a[href*='rename']")
    private WebElement sidebarRename;
    @FindBy(xpath = "//a[contains(@href,'move')]")
    private WebElement sidebarMove;

    @FindBy(css = "[class*='dropdown'] [href$='Delete']")
    private WebElement breadcrumbsDelete;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement breadcrumbsRename;

    @FindBy(css = "[class*='dropdown'] [href$='move']")
    private WebElement breadcrumbsMove;

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsFirstName;

    @FindBys({
        @FindBy(id = "tasks"),
        @FindBy(className = "task-link-text")
    })
    private List<WebElement> taskList;

    @FindBy(css = "[class^='task-link-wrapper']")
    private List<WebElement> sidebarTasksList;

    @FindBy(css = "#breadcrumbBar li:last-child")
    private WebElement breadcrumbs;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get item name on Project page")
    public String getProjectName() {
        return projectName.getText();
    }

    @Step("Click on the 'Add description'")
    public T clickAddDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Click on the 'Edit description'")
    public T clickEditDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Check 'Add description button")
    public boolean isAddDescriptionButtonEnable() {
        return addOrEditDescriptionButton.isEnabled();
    }

    @Step("Clear previous description")
    public T clearDescription() {
        descriptionInput.clear();

        return (T) this;
    }

    @Step("Type description text in the input text area")
    public T typeDescription(String text) {
        descriptionInput.sendKeys(text);

        return (T) this;
    }

    @Step("Click on description texarea to make it active")
    public T clickOnDescriptionInput() {
        descriptionInput.click();

        return (T) this;
    }

    public String getColorOfDefaultTextAreaBorderBacklight() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                descriptionInput);
    }

    @Step("Click on the 'Save' button")
    public T clickSaveButton() {
        saveButton.click();

        return (T) this;
    }

    @Step("Get description text")
    public String getDescriptionText() {
        return displayedDescription.getText();
    }

    public T clickShowDescriptionPreview() {
        showDescriptionPreview.click();

        return (T) this;
    }

    public T clickHideDescriptionPreview() {
        hideDescriptionPreview.click();

        return (T) this;
    }

    public boolean isDescriptionPreviewVisible() {
        return descriptionPreview.isDisplayed();
    }

    public String getColorOfTextAreaBorderBacklight() {
        getWait2().until(ExpectedConditions.invisibilityOf(addOrEditDescriptionButton));

        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    @Step("Click on the 'Delete' on the sidebar")
    public T clickDeleteOnSidebar() {
        sidebarDelete.click();

        return (T) this;
    }

    @Step("Click on the 'Delete' on the breadcrumbs")
    public T clickDeleteOnBreadcrumbsMenu() {
        breadcrumbsDelete.click();

        return (T) this;
    }

    @Step("Click 'Yes' for confirmation delete item from Home Page")
    public HomePage clickYesWhenDeletedItemOnHomePage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return new HomePage(getDriver());
    }

    @Step("Click 'Yes' for confirmation delete item from Folder")
    public FolderProjectPage clickYesWhenDeletedItemInFolder() {
        getWait2().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return new FolderProjectPage(getDriver());
    }

    public String getYesButtonColorDeletingViaSidebar() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--color');",
                yesButton);
    }

    @Step("Click 'Rename' on the sidebar")
    public ProjectRenamePage<T> clickRenameOnSidebar() {
        sidebarRename.click();

        return new ProjectRenamePage<>(getDriver(), (T) this);
    }

    @Step("Click 'Rename' on the breadcrumbs")
    public ProjectRenamePage<T> clickRenameOnBreadcrumbsMenu() {
        breadcrumbsRename.click();

        return new ProjectRenamePage<>(getDriver(), (T) this);
    }

    public String getErrorText() {
        return new RenameErrorPage(getDriver()).getErrorText();
    }

    @Step("Check task presence on sidebar")
    public boolean isTaskPresentOnSidebar(String task) {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(taskList));

        return taskList.stream()
                .anyMatch(element -> task.equals(element.getText()));
    }

    @Step("Hover cursor over project name on breadcrumbs to show arrow")
    public T hoverOverProjectNameOnBreadcrumbs(String name) {
        hoverOverElement(getDriver().findElement(By.linkText(name)));

        return (T) this;
    }

    @Step("Click breadcrumbs arrow after project name to call menu")
    public T clickBreadcrumbsArrowAfterProjectName(String name) {
        WebElement arrowAfterName = getDriver().findElement(
                By.cssSelector("[href$='" + name + "/'] [class$='dropdown-chevron']"));

        clickBreadcrumbsDropdownArrow(arrowAfterName);

        return (T) this;
    }

    @Step("Click specific Folder name on breadcrumbs")
    public FolderProjectPage clickFolderNameOnBreadcrumbs(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new FolderProjectPage(getDriver());
    }

    @Step("Get Item name from breadcrumbs")
    public String getBreadcrumbName() {
        return breadcrumbsFirstName.getText();
    }

    @Step("Click 'Move' on the sidebar Menu")
    public ProjectMovePage<T> clickMoveOnSidebar() {
        sidebarMove.click();

        return new ProjectMovePage<>(getDriver(), (T) this);
    }

    @Step("Click 'Move' on the breadcrumbs dropdown Menu")
    public ProjectMovePage<T> clickMoveOnBreadcrumbs() {
        breadcrumbsMove.click();

        return new ProjectMovePage<>(getDriver(), (T) this);
    }

    public String getColorOfAddDescriptionButtonBackground() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before')"
                + ".getPropertyValue('--item-background--hover');", addOrEditDescriptionButton);
    }

    public T hoverOnAddDescriptionButton() {
        hoverOverElement(addOrEditDescriptionButton);

        return (T) this;
    }

    public List<String> getSidebarTasksListHavingExistingFolder() {
        return sidebarTasksList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public Integer getSidebarTasksSize() {
        return sidebarTasksList.size();
    }

    public boolean isProjectInsideFolder(String jobName, String folderName) {
        return breadcrumbs.getAttribute("data-href").contains(folderName + "/job/" + jobName);
    }

}
