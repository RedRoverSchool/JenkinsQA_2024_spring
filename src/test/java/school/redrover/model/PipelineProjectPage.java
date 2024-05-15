package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineProjectPage extends BaseProjectPage {

    @FindBy(id = "description-link")
    private WebElement changeDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description>:first-child")
    private WebElement displayedDescription;

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

    @FindBy(xpath = "//a[@data-build-success = 'Build scheduled']")
    private WebElement buildButton;

    @FindBy(xpath = "//td[contains(@class, 'progress-bar')]")
    private WebElement buildProgressBar;

    @FindBy(css = "[aria-describedby^='tippy'")
    private WebElement buildScheduledPopUp;

    @FindBy(xpath = "//div[@id = 'buildHistory']//tr[@class != 'build-search-row']")
    private List<WebElement> listOfBuilds;

    @FindBy(xpath = "//div[@class='pane-content']//a[contains(text(),'#')]")
    private List<WebElement> buildHistoryNumberList;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    @FindBys({
            @FindBy(id = "tasks"),
            @FindBy(className = "task-link-text")
    })

    private List<WebElement> taskLinkTextElements;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//div[contains(text(), 'Full project name:')]")
    private WebElement fullProjectNameLocation;

    @FindBy(css = "[class*='dropdown__item'][href$='changes']")
    private WebElement dropdownChangesButton;

    @FindBy(css = "[class*='dropdown'] [href$='rename']")
    private WebElement breadcrumbsRenameButton;

    @FindBy(xpath = "//th[contains(@class, 'stage-header-name')]")
    private List<WebElement> stageHeader;

    @FindBy(className = "date")
    private WebElement stageDate;

    @FindBy(className = "time")
    private WebElement stageTime;

    @FindBy(className = "badge")
    private WebElement stageBadge;

    @FindBy(xpath = "//div[@class='changeset-box no-changes']")
    private WebElement stageStatus;


    @FindBy(className = "stage-total-0")
    private WebElement avgStageTime;

    @FindBy(className = "table-box")
    private WebElement stageTable;

    @FindBy(css = "form > button")
    private WebElement disablebutton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement enableButton;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinkList;

    @FindBy(xpath = "//*[@tooltip='Success']")
    private WebElement buildStatusMark;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public PipelineProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public PipelineProjectPage clickChangeDescription() {
        changeDescriptionButton.click();

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
        getWait2().until(ExpectedConditions.invisibilityOf(changeDescriptionButton));

        return this;
    }

    public String getTextAreaBorderBacklightColor() {
        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    public PipelineProjectPage clickOnDescriptionInput() {
        descriptionInput.click();

        return this;
    }

    public PipelineProjectPage makeDescriptionFieldNotActive() {
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        return this;
    }

    public String getDefaultTextAreaBorderBacklightColor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                descriptionInput);
    }

    public boolean isDescriptionVisible(String pipelineDescription) {
        return getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[text()='" + pipelineDescription + "']"))).isDisplayed();
    }

    public DeleteDialog clickSidebarDeleteButton() {
        sidebarDeleteButton.click();

        return new DeleteDialog(getDriver());
    }

    public PipelineConfigPage clickSidebarConfigureButton(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/configure']")).click();

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

    public PipelineProjectPage clickBuild() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildButton)).click();

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

    public boolean isBtnPresentInSidebar(String btnText) {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(taskLinkTextElements));

        return taskLinkTextElements.stream()
                .anyMatch(element -> btnText.equals(element.getText()));
    }

    public String getWarningMessageText() {
        return getWait2().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    public String getFullStageViewButtonBackgroundColor() {
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

    public void waitStageTable() {
        getWait10().until(ExpectedConditions.visibilityOf(stageTable));
    }

    public boolean avgStageTimeAppear() {
        return avgStageTime.isDisplayed();
    }

    public boolean buildTimeAppear(int buildNumber) {
        return getDriver().findElement(By.xpath("//tr[@data-runid='"
                + buildNumber + "']//td[@data-stageid='6']")).isDisplayed();
    }

    public boolean isDisableButtonVisible() {
        return getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.name("Submit"))).isDisplayed();
    }

    public PipelineProjectPage clickDisableButton() {
        disablebutton.click();

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
}
