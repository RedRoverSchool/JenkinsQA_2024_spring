package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

    @FindBy(css = "#disable-project button")
    private WebElement disableProjectButton;

    @FindBy(css = "#enable-project button")
    private WebElement enableButton;

    @FindBy(css = "#description > div:first-child")
    private WebElement projectDescription;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//a[contains(@href,'move')]")
    private WebElement moveButton;

    @FindBy(xpath = "//a[contains(@href, 'configure')]")
    private WebElement configureButton;

    @FindBy(xpath = "//a[contains(@href, 'confirm-rename')]")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@id = 'tasks']/div[6]/span")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowSideBar;

    @FindBy(xpath = "//*[@class='model-link inside build-link display-name']")
    private WebElement buildInfo;

    @FindBy(xpath = "//div[contains(text(), 'Full project name:')]")
    private WebElement projectPath;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(xpath = "//a[@tooltip='Success > Console Output']")
    private WebElement successConsoleOutputButton;

    @FindBy(xpath = "//form[@id='enable-project']")
    private WebElement disabledStatusMassage;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(xpath = "//div[@class='build-icon']")
    private WebElement greenMarkBuildSuccess;

    @FindBy(xpath = "//a[text()='Add description']")
    private WebElement addDescriptionButton;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Add description' button")
    public FreestyleProjectPage clickAddDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    @Step("Click 'Edit description'")
    public FreestyleProjectPage clickEditDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    @Step("Click 'Disable Project' button")
    public FreestyleProjectPage clickDisableProjectButton() {
        disableProjectButton.click();

        return this;
    }

    public String getDisableProjectButtonText() {

        return disableProjectButton.getText();
    }

    @Step("Click 'Enable Project'")
    public FreestyleProjectPage clickEnableButton() {
        enableButton.click();

        return this;
    }

    @Step("Clear previous description")
    public FreestyleProjectPage clearDescription() {
        descriptionInput.clear();

        return this;
    }

    @Step("Type description text in the input description field")
    public FreestyleProjectPage setDescription(String name) {
        descriptionInput.sendKeys(name);

        return this;
    }

    @Step("Click 'Save' button")
    public FreestyleProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    @Step("Click breadcrumbs dropdown menu for the project")
    public FreestyleProjectPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    @Step("Click 'Rename' from breadcrumbs dropdown menu")
    public RenameDialogPage clickBreadcrumbsDropdownRenameProject(String oldItemName) {
        getDriver().findElement(By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/" + oldItemName + "/confirm-rename']")).click();

        return new RenameDialogPage(getDriver());
    }

    public String getProjectDescriptionText() {
        return projectDescription.getText();
    }

    @Step("Click 'Move' on sidebar Menu")
    public FreestyleMovePage clickMove() {
        moveButton.click();
        return new FreestyleMovePage(getDriver());
    }

    @Step("Click 'Configure' on sidebar menu")
    public FreestyleConfigPage clickConfigure() {

        configureButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    @Step("Click 'Rename' on sidebar menu")
    public FreestyleRenamePage clickRename() {

        renameButton.click();

        return new FreestyleRenamePage(getDriver());
    }

    @Step("Click 'Delete Project' on sidebar menu")
    public FreestyleProjectPage clickDelete() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        return this;
    }

    @Step("Click 'Yes' button in confirming deletion dialog")
    public HomePage clickYesInConfirmDeleteDialog() {
        yesButton.click();
        return new HomePage(getDriver());
    }

    public boolean isAddDescriptionButtonEnable() {

        return addDescriptionButton.isEnabled();
    }

    @Step("Click 'Build Now' on sidebar menu")
    public FreestyleProjectPage clickBuildNowOnSideBar() {
        buildNowSideBar.click();
        return this;
    }

    public String getBuildInfo() {
        String buildHistoryStatus = getDriver().findElement(By.id("buildHistory")).getAttribute("class");

        if (buildHistoryStatus.contains("collapsed")) {
            getDriver().findElement(By.xpath("//a[@href='/toggleCollapse?paneId=buildHistory']")).click();
        }

        return buildInfo.getText();
    }

    @Step("Wait for green mark appearance, indicating that project is successfully build")
    public FreestyleProjectPage waitForGreenMarkBuildSuccessAppearance() {
        getWait10().until(ExpectedConditions.visibilityOf(greenMarkBuildSuccess));

        return this;
    }

    public String getFullProjectPath() {
        return projectPath.getText();
    }

    public String getPageHeadingText() {
        return pageHeading.getText();
    }

    public JobBuildConsolePage clickSuccessConsoleOutputButton() {
        getWait60().until(ExpectedConditions.elementToBeClickable(successConsoleOutputButton)).click();

        return new JobBuildConsolePage(getDriver());
    }

    public String getDisabledMassageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(disabledStatusMassage)).getText();
    }
}