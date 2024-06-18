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

    @FindBy(xpath = "//a[@tooltip='Success > Console Output']")
    private WebElement successConsoleOutputButton;

    @FindBy(xpath = "//form[@id='enable-project']")
    private WebElement disabledStatusMessage;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(xpath = "//div[@class='build-icon']")
    private WebElement greenMarkBuildSuccess;


    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Disable Project' button")
    public FreestyleProjectPage clickDisableProjectButton() {
        disableProjectButton.click();

        return this;
    }

    public String getDisableProjectButtonText() {
        return disableProjectButton.getText();
    }

    @Step("Click on the 'Enable Project'")
    public FreestyleProjectPage clickEnableButton() {
        enableButton.click();

        return this;
    }

    @Step("Click on the breadcrumbs dropdown menu for the project")
    public FreestyleProjectPage clickBreadcrumbsArrowAfterName() {
        clickBreadcrumbsDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    @Step("Click on the 'Rename' from breadcrumbs dropdown menu")
    public RenameDialogPage clickBreadcrumbsDropdownRenameProject(String oldItemName) {
        getDriver().findElement(By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/" + oldItemName + "/confirm-rename']")).click();

        return new RenameDialogPage(getDriver());
    }

    @Step("Click on the 'Configure' on sidebar menu")
    public FreestyleConfigPage clickConfigure() {
        configureButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    @Step("Click on the 'Rename' on sidebar menu")
    public FreestyleRenamePage clickRename() {
        renameButton.click();

        return new FreestyleRenamePage(getDriver());
    }

    @Step("Click on the 'Delete Project' on sidebar menu")
    public FreestyleProjectPage clickDelete() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

        return this;
    }

    @Step("Click 'Yes' button in confirming deletion dialog")
    public HomePage clickYesInConfirmDeleteDialog() {
        yesButton.click();

        return new HomePage(getDriver());
    }

    @Step("Click on the 'Build Now' on sidebar menu")
    public FreestyleProjectPage clickBuildNowOnSideBar() {
        buildNowSideBar.click();
        getWait10().until(ExpectedConditions.visibilityOf(greenMarkBuildSuccess));

        return this;
    }

    public String getBuildInfo() {
        String buildHistoryStatus = getDriver().findElement(By.id("buildHistory")).getAttribute("class");

        if (buildHistoryStatus.contains("collapsed")) {
            getDriver().findElement(By.xpath("//a[@href='/toggleCollapse?paneId=buildHistory']")).click();
        }

        return buildInfo.getText();
    }

    public String getFullProjectPath() {
        return projectPath.getText();
    }

    public String getPageHeadingText() {
        return pageHeading.getText();
    }

    @Step("Click 'Success Console Output' button")
    public JobBuildConsolePage clickSuccessConsoleOutputButton() {
        getWait60().until(ExpectedConditions.elementToBeClickable(successConsoleOutputButton)).click();

        return new JobBuildConsolePage(getDriver());
    }

    public String getDisabledMessageText() {

        return getWait5().until(ExpectedConditions.visibilityOf(disabledStatusMessage)).getText();
    }
}
