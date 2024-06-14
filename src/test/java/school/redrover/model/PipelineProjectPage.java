package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

import java.util.*;
import java.util.stream.Collectors;

public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage> {

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description>:first-child")
    private WebElement displayedDescription;

    @FindBy(css = ".textarea-preview")
    private WebElement descriptionPreview;

    @FindBy(css = ".textarea-show-preview")
    private WebElement showDescriptionPreview;

    @FindBy(css = ".textarea-hide-preview")
    private WebElement hideDescriptionPreview;

    @FindBy(css = "[data-title='Delete Pipeline']")
    private WebElement sidebarDeleteButton;

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "[href^='/job'] [class$='dropdown-chevron']")
    private WebElement breadcrumbsDropdownArrow;

    @FindBy(css = "[class*='dropdown'] [href$='Delete']")
    private WebElement breadcrumbsDeleteButton;

    @FindBy(css = "a[href$='rename']")
    private WebElement sidebarRenameButton;

    @FindBy(css = "a[href$='configure']")
    private WebElement sidebarConfigureButton;

    @FindBy(css = "[data-build-success]")
    private WebElement buildNowOnSidebar;

    @FindBy(xpath = "//td[contains(@class, 'progress-bar')]")
    private WebElement buildProgressBar;

    @FindBy(css = "[aria-describedby^='tippy'")
    private WebElement buildScheduledPopUp;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//div[contains(text(), 'Full project name:')]")
    private WebElement fullProjectNameLocation;

    @FindBy(css = "[class*='dropdown__item'][href$='changes']")
    private WebElement dropdownChangesButton;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement breadcrumbsRenameButton;

    @FindBy(className = "date")
    private WebElement stageDate;

    @FindBy(className = "time")
    private WebElement stageTime;

    @FindBy(className = "badge")
    private WebElement stageBadge;

    @FindBy(xpath = "//div[@class='changeset-box no-changes']")
    private WebElement stageStatus;

    @FindBy(css = "form > button")
    private WebElement disableProjectButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement enableButton;

    @FindBy(xpath = "//*[@tooltip='Success']")
    private WebElement buildStatusMark;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinkList;

    @FindBy(xpath = "//th[contains(@class,'stage-header-name')]")
    private List<WebElement> stageHeaderNameList;

    @FindBy(xpath = "//div[@id = 'buildHistory']//tr[@class != 'build-search-row']")
    private List<WebElement> listOfBuilds;

    @FindBy(xpath = "//div[@class='pane-content']//a[contains(text(),'#')]")
    private List<WebElement> buildHistoryNumberList;

    @FindBy(xpath = "//th[contains(@class, 'stage-header-name')]")
    private List<WebElement> stageHeader;

    @FindBys({
            @FindBy(id = "tasks"),
            @FindBy(className = "task-link-text")
    })
    private List<WebElement> taskList;

    @FindBy(xpath = "//h1[@class='job-index-headline page-headline']")
    private WebElement projectsDisplayNameInHeader;

    @FindBy(css = "span[class='glyphicon glyphicon-stats']")
    private WebElement buildStageLogsButton;

    @FindBy(css = "span[class='glyphicon glyphicon-remove']")
    private WebElement closeStageLogsButton;

    @FindBy(css = ".build-row-cell")
    private List<WebElement> buildRow;


    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public PipelineProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public PipelineProjectPage clickChangeDescription() {
        addOrEditDescriptionButton.click();

        return this;
    }

    public PipelineProjectPage setDescription(String name) {
        descriptionInput.sendKeys(name);

        return this;
    }

    public String getDescriptionText() {
        return displayedDescription.getText();
    }

    public PipelineProjectPage waitAddDescriptionButtonDisappears() {
        getWait2().until(ExpectedConditions.invisibilityOf(addOrEditDescriptionButton));

        return this;
    }

    public PipelineProjectPage clickShowDescriptionPreview() {
        showDescriptionPreview.click();
        return this;
    }

    public PipelineProjectPage clickHideDescriptionPreview() {
        hideDescriptionPreview.click();
        return this;
    }

    public boolean isDescriptionPreviewVisible() {
        return descriptionPreview.isDisplayed();
    }

    public String getColorOfTextAreaBorderBacklight() {
        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    public String getColorOfCell() {
        Set<String> backgroundColor = new HashSet<>();
        for (int i = 1; i <= 2; i++) {
            WebElement element = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@data-runid='" + i + "']/td[@class='stage-cell stage-cell-0 SUCCESS']/div[@class='cell-color']")));

            backgroundColor.add(element.getCssValue("background-color"));
        }
        return backgroundColor.iterator().next();
    }

    public PipelineProjectPage clickOnDescriptionInput() {
        descriptionInput.click();

        return this;
    }

    public PipelineProjectPage makeDescriptionFieldNotActive() {
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        return this;
    }

    public String getColorOfDefaultTextAreaBorderBacklight() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                descriptionInput);
    }

    public DeleteDialog clickSidebarDeleteButton() {
        sidebarDeleteButton.click();

        return new DeleteDialog(getDriver());
    }

    @Step("Click on the 'Configure' on sidebar")
    public PipelineConfigPage clickConfigureOnSidebar() {
        sidebarConfigureButton.click();

        return new PipelineConfigPage(getDriver());
    }

    public PipelineProjectPage hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return this;
    }

    public PipelineProjectPage clickBreadcrumbsDropdownArrow() {
        clickSpecificDropdownArrow(breadcrumbsDropdownArrow);

        return this;
    }

    public DeleteDialog clickBreadcrumbsDeleteButton() {
        breadcrumbsDeleteButton.click();

        return new DeleteDialog(getDriver());
    }

    public PipelineRenamePage clickSidebarRenameButton() {
        sidebarRenameButton.click();

        return new PipelineRenamePage(getDriver());
    }

    @Step("Click on 'Build Now' on sidebar")
    public PipelineProjectPage clickOnBuildNowOnSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildNowOnSidebar)).click();

        return this;
    }

    public PipelineProjectPage waitForBuildScheduledPopUp() {
        getWait2().until(ExpectedConditions.visibilityOf(buildScheduledPopUp));

        return this;
    }

    public PipelineProjectPage waitBuildToFinish() {
        getWait10().until(ExpectedConditions.invisibilityOf(buildProgressBar));

        return this;
    }

    public boolean isBuildAppear(int buildNumber, String jobName) {
        getDriver().navigate().refresh();
        WebElement nBuild = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class = 'build-row-cell']//a[text() = '#" + buildNumber + "']")));

        return nBuild.getAttribute("href").contains("/job/" + jobName.replaceAll(" ", "%20") + "/2/");
    }

    public int numberOfBuild() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(listOfBuilds)).size();
    }

    public FullStageViewPage clickFullStageViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(fullStageViewButton)).click();

        return new FullStageViewPage(getDriver());
    }

    @Step("Check task presenсe on sidebar")
    public boolean isTaskPresentOnSidebar(String task) {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(taskList));

        return taskList.stream()
                .anyMatch(element -> task.equals(element.getText()));
    }

    @Step("Get message about Pipeline status")
    public String getWarningMessageText() {
        return getWait2().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    public String getColorOfFullStageViewButtonBackground() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('background-color');", fullStageViewButton);
    }

    public PipelineProjectPage hoverOnFullStageViewButton() {
        new Actions(getDriver()).scrollToElement(fullStageViewButton)
                .moveToElement(fullStageViewButton)
                .pause(2000)
                .perform();

        return this;
    }

    public PipelineProjectPage makeBuilds(int buildsQtt) {
        for (int i = 1; i <= buildsQtt; i++) {
            getWait5().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/build?delay=0sec')]"))).click();

            getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tr[@data-runid='" + i + "']")));
        }
        return this;
    }

    public String getFullProjectNameLocationText() {
        return fullProjectNameLocation.getText();
    }

    public PipelineChangesPage clickDropdownChangesButton() {
        dropdownChangesButton.click();

        return new PipelineChangesPage(getDriver());
    }

    public PipelineRenamePage clickBreadcrumbsRenameButton() {
        breadcrumbsRenameButton.click();

        return new PipelineRenamePage(getDriver());
    }

    public int getSagesQtt() {

        return stageHeader.size();
    }

    public boolean getBuildAttributeStatus() {
        boolean result = true;
        if (stageDate == null || !stageDate.isDisplayed()) {
            result = false;
        }
        if (stageTime == null || !stageTime.isDisplayed()) {
            result = false;
        }
        if (stageStatus == null || !(stageStatus.getText().equals("No Changes"))) {
            result = false;
        }
        if (stageBadge == null || !stageBadge.isDisplayed()) {
            result = false;
        }
        return result;
    }

    @Step("Check visibility of button 'Disable Project'")
    public boolean isDisableButtonVisible() {
        return getWait2().until(ExpectedConditions
                .visibilityOf(disableProjectButton)).isDisplayed();
    }

    @Step("Click on button 'Disable' on Pipeline project page")
    public PipelineProjectPage clickDisableButton() {
        disableProjectButton.click();

        return this;
    }

    public PipelineProjectPage clickEnableButton() {
        enableButton.click();

        return this;
    }

    public List<String> getPermalinkList() {

        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinkList))
                .stream()
                .map(WebElement::getText)
                .map(permalink -> permalink.split(",")[0].trim())
                .collect(Collectors.toList());
    }

    public String getHexColorSuccessMark() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement statusMark = getWait10().until(ExpectedConditions.visibilityOf(buildStatusMark));

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--success');",
                statusMark);
    }

    public List<String> getBuildHistoryList() {
        List<String> buildOrderList = new ArrayList<>();
        for (WebElement buildNumber : buildHistoryNumberList) {
            buildOrderList.add(buildNumber.getText());
        }

        return buildOrderList;
    }

    public List<String> getExpectedBuildHistoryDescendingList() {
        List<String> buildOrderList = new ArrayList<>(getBuildHistoryList());
        buildOrderList.sort(Collections.reverseOrder());

        return buildOrderList;
    }

    public List<String> getStageHeaderNameList() {

        List<String> headerList = new ArrayList<>();
        for (WebElement stageHeaderElement : getWait10().until(ExpectedConditions.visibilityOfAllElements(stageHeaderNameList))) {
            headerList.add(stageHeaderElement.getText());
        }
        return headerList;
    }

    public String getProjectsDisplayNameInHeader() {
        return projectsDisplayNameInHeader.getText();
    }

    public List<String> getConsoleOuputForAllStages(int numberOfStages) {
        List<String> consoleOuputForAllStages = new ArrayList<>();
        for (int i = 1; i <= numberOfStages; i++) {
            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[class='stage-cell stage-cell-" + (i - 1) + " SUCCESS']"))).click();
            buildStageLogsButton.click();

            consoleOuputForAllStages.add(getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("pre[class='console-output']"))).getText());

            closeStageLogsButton.click();
            getWait2().until(ExpectedConditions.invisibilityOf(closeStageLogsButton));
        }

        return consoleOuputForAllStages;
    }

    public List<String> getBuilderRowList() {

        return getWait5().until(ExpectedConditions.visibilityOfAllElements(buildRow)).stream()
                .map(WebElement::getText).toList();
    }
}
