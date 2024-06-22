package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage> {

    @FindBy(css = "a[href$='configure']")
    private WebElement sidebarConfigureButton;

    @FindBy(css = "[data-build-success]")
    private WebElement buildNowOnSidebar;

    @FindBy(xpath = "//td[contains(@class, 'progress-bar')]")
    private WebElement buildProgressBar;

    @FindBy(css = "[aria-describedby^='tippy']")
    private WebElement buildScheduledPopUp;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//div[contains(text(), 'Full project name:')]")
    private WebElement fullProjectNameLocation;

    @FindBy(css = "[class*='dropdown__item'][href$='changes']")
    private WebElement dropdownChangesButton;

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

    @FindBy(css = "span[class='glyphicon glyphicon-stats']")
    private WebElement buildStageLogsButton;

    @FindBy(css = "span[class='glyphicon glyphicon-remove']")
    private WebElement closeStageLogsButton;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get color of cell background")
    public String getColorOfCell() {
        Set<String> backgroundColor = new HashSet<>();
        for (int i = 1; i <= 2; i++) {
            WebElement element = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@data-runid='" + i
                            + "']/td[@class='stage-cell stage-cell-0 SUCCESS']/div[@class='cell-color']")));

            backgroundColor.add(element.getCssValue("background-color"));
        }
        return backgroundColor.iterator().next();
    }

    @Step("Make the description field not active by sending a TAB key")
    public PipelineProjectPage makeDescriptionFieldNotActive() {
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        return this;
    }

    @Step("Click on the 'Configure' on sidebar")
    public PipelineConfigPage clickConfigureOnSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(sidebarConfigureButton)).click();

        return new PipelineConfigPage(getDriver());
    }

    @Step("Click on 'Build Now' on sidebar")
    public PipelineProjectPage clickBuildNowOnSidebarAndWait() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildNowOnSidebar)).click();
        getWait2().until(ExpectedConditions.visibilityOf(buildScheduledPopUp));
        getWait10().until(ExpectedConditions.invisibilityOf(buildProgressBar));

        return this;
    }

    public boolean isBuildAppear(int buildNumber, String jobName) {
        getDriver().navigate().refresh();
        WebElement nBuild = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class = 'build-row-cell']//a[text() = '#" + buildNumber + "']")));

        return nBuild.getAttribute("href").contains("/job/" + jobName.replaceAll(" ", "%20") + "/2/");
    }

    public int numberOfBuild() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(listOfBuilds)).size();
    }

    @Step("Click 'Full Stage View' on Sidebar menu")
    public FullStageViewPage clickFullStageViewOnSidebarMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(fullStageViewButton)).click();

        return new FullStageViewPage(getDriver());
    }

    @Step("Get message about Pipeline status")
    public String getWarningMessageText() {
        return getWait2().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    public String getColorOfFullStageViewButtonBackground() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('background-color');",
                fullStageViewButton);
    }

    @Step("Hover on 'Full Stage View' on Sidebar menu")
    public PipelineProjectPage hoverOnFullStageViewButton() {
        new Actions(getDriver()).scrollToElement(fullStageViewButton)
                .moveToElement(fullStageViewButton)
                .pause(2000)
                .perform();

        return this;
    }

    @Step("Click 'Build Now' certain amount times")
    public PipelineProjectPage makeBuilds(int buildsQtt) {
        for (int i = 1; i <= buildsQtt; i++) {
            getWait5().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/build?delay=0sec')]"))).click();

            getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tr[@data-runid='" + i + "']")));
        }

        getWait10().until(ExpectedConditions.invisibilityOf(buildProgressBar));

        return this;
    }

    public String getFullProjectNameLocationText() {
        return fullProjectNameLocation.getText();
    }

    @Step("Click 'Changes' on dropdown menu")
    public PipelineChangesPage clickChangesOnDropdownMenu() {
        dropdownChangesButton.click();

        return new PipelineChangesPage(getDriver());
    }

    public int getStagesQuantity() {

        return stageHeader.size();
    }

    @Step("Check visibility of button 'Disable Project'")
    public boolean isDisableButtonVisible() {
        return getWait2().until(ExpectedConditions
                .visibilityOf(disableProjectButton)).isDisplayed();
    }

    @Step("Click on the button 'Disable' on Pipeline project page")
    public PipelineProjectPage clickDisableButton() {
        disableProjectButton.click();

        return this;
    }

    @Step("Click on the button 'Enable' on Pipeline project page")
    public PipelineProjectPage clickEnableButton() {
        enableButton.click();

        return this;
    }

    @Step("Get permalink list")
    public List<String> getPermalinkList() {

        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinkList))
                .stream()
                .map(WebElement::getText)
                .map(permalink -> permalink.split(",")[0].trim())
                .collect(Collectors.toList());
    }

    @Step("Get color of Succes Mark")
    public String getHexColorSuccessMark() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement statusMark = getWait10().until(ExpectedConditions.visibilityOf(buildStatusMark));

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--success');",
                statusMark);
    }

    @Step("Get Build history list")
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

    @Step("Get Stage header name list")
    public List<String> getStageHeaderNameList() {
        List<String> headerList = new ArrayList<>();
        for (WebElement stageHeaderElement : getWait10().until(
                ExpectedConditions.visibilityOfAllElements(stageHeaderNameList))) {
            headerList.add(stageHeaderElement.getText());
        }

        return headerList;
    }

    @Step("Get console output for all stages")
    public List<String> getConsoleOutputForAllStages(int numberOfStages) {
        List<String> consoleOutputForAllStages = new ArrayList<>();
        for (int i = 1; i <= numberOfStages; i++) {
            getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("td[class='stage-cell stage-cell-" + (i - 1) + " SUCCESS']"))).click();
            buildStageLogsButton.click();

            consoleOutputForAllStages.add(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("pre[class='console-output']"))).getText());

            closeStageLogsButton.click();
            getWait2().until(ExpectedConditions.invisibilityOf(closeStageLogsButton));
        }
        return consoleOutputForAllStages;
    }
}
