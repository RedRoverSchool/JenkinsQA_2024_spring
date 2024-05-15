package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage {

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[3]/a")
    private WebElement projectNameFromBreadcrumbs;

    @FindBy(css = "#disable-project button")
    private WebElement disableProjectButton;

    @FindBy(css = "#enable-project button")
    private WebElement enableButton;

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    @FindBy(xpath = "//*[@id='main-panel']//h1")
    private WebElement projectName;

    @FindBy(css = "#description > div:first-child")
    private WebElement projectDescription;

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

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

    @FindBy(xpath = "//td[contains(@class, 'progress-bar')]")
    private WebElement buildProgressBar;

    @FindBy(xpath = "//*[@class='model-link inside build-link display-name']")
    private WebElement buildInfo;

    @FindBy(xpath = "//div[contains(text(), 'Full project name:')]")
    private WebElement projectPath;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }

    public FreestyleProjectPage clickAddDescription() {
        addDescriptionButton.click();

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

    public String getProjectName() {

        return projectName.getText();
    }

    public String getProjectNameFromBreadcrumbs() {

        return projectNameFromBreadcrumbs.getText();
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

    public FreestyleProjectPage deleteFreestyleProject() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        return this;
    }

    public HomePage confirmDeleteFreestyleProject() {
        yesButton.click();
        return new HomePage(getDriver());
    }

    public FolderProjectPage clickBreadcrumbFolder(String name) {

        getDriver().findElement(By.xpath("//a[@href='/job/" + name + "/']")).click();
        return new FolderProjectPage(getDriver());

    }

    public boolean isProjectNameDisplayed() {
        return projectName.isDisplayed();
    }

    public FreestyleProjectPage clickBuildNowOnSideBar() {
        buildNowSideBar.click();
        return this;
    }

    public FreestyleProjectPage waitBuildToFinish() {
        getWait10().until(ExpectedConditions.invisibilityOf(buildProgressBar));

        return this;
    }

    public String getBuildInfo() {

        return buildInfo.getText();
    }

    public String getFullProjectPath() {
        return projectPath.getText();
    }
}