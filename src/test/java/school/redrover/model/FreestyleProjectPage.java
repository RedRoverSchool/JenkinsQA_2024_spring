package school.redrover.model;

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

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    @FindBy(tagName = "h1")
    private WebElement projectName;

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

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }

    public FreestyleProjectPage clickAddDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public FreestyleProjectPage clickEditDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public FreestyleProjectPage clickDisableProjectButton() {
        disableProjectButton.click();

        return this;
    }

    public String getDisableProjectButtonText() {

        return disableProjectButton.getText();
    }

    public FreestyleProjectPage clickEnableButton() {
        enableButton.click();

        return this;
    }

    public FreestyleProjectPage clearDescription() {
        descriptionInput.clear();

        return this;
    }

    public FreestyleProjectPage setDescription(String name) {
        descriptionInput.sendKeys(name);

        return this;
    }

    public FreestyleProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public FreestyleProjectPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    public RenameDialogPage clickBreadcrumbsDropdownRenameProject(String oldItemName) {
        getDriver().findElement(By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/" + oldItemName + "/confirm-rename']")).click();

        return new RenameDialogPage(getDriver());
    }

    public String getProjectDescriptionText() {
        return projectDescription.getText();
    }

    public FreestyleMovePage clickMove() {
        moveButton.click();
        return new FreestyleMovePage(getDriver());
    }

    public FreestyleConfigPage clickConfigure() {

        configureButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleRenamePage clickRename() {

        renameButton.click();

        return new FreestyleRenamePage(getDriver());
    }

    public FreestyleProjectPage clickDelete() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        return this;
    }

    public HomePage clickYesInConfirmDeleteDialog() {
        yesButton.click();
        return new HomePage(getDriver());
    }

    public boolean isAddDescriptionButtonEnable() {

        return addDescriptionButton.isEnabled();
    }

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

    public FreestyleProjectPage waitForGreenMarkBuildSuccessAppearience() {
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

    public String getDesabledMassageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(disabledStatusMassage)).getText();
    }
}