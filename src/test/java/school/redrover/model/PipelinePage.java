package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class PipelinePage extends BasePage {

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

    @FindBy(css = "div > h1")
    private WebElement headlineDisplayedName;

    @FindBy(xpath = "//a[@data-build-success = 'Build scheduled']")
    private WebElement buildButton;

    @FindBy(xpath = "//td[contains(@class, 'progress-bar')]")
    private WebElement buildProgressBar;

    @FindBy(xpath = "//div[@id = 'buildHistory']//tr[@class != 'build-search-row']")
    private List<WebElement> listOfBuilds;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    @FindBys({
            @FindBy(id = "tasks"),
            @FindBy(className = "task-link-text")
    })
    private List<WebElement> taskLinkTextElements;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public PipelinePage clickChangeDescription() {
        changeDescriptionButton.click();

        return this;
    }

    public PipelinePage setDescription(String name) {
        descriptionInput.sendKeys(name);

        return this;
    }

    public String getDescriptionText() {
        return displayedDescription.getText();
    }

    public PipelinePage waitAddDescriptionButtonDisappears() {
        getWait2().until(ExpectedConditions.invisibilityOf(changeDescriptionButton));

        return this;
    }

    public String getTextAreaBorderBacklightColor() {
        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    public PipelinePage clickOnDescriptionInput() {
        descriptionInput.click();

        return this;
    }

    public PipelinePage makeDescriptionFieldNotActive() {
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

    public PipelinePage hoverOverBreadcrumbsName() {
        hoverOverElement(breadcrumbsName);

        return this;
    }

    public PipelinePage clickBreadcrumbsDropdownArrow() {
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

    public String getHeadlineDisplayedName() {
        return headlineDisplayedName.getText();
    }

    public PipelinePage clickBuild() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildButton)).click();

        return this;
    }

    public PipelinePage waitBuildToFinish() {
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

    public PipelinePage hoverOnFullStageViewButton() {
        new Actions(getDriver()).scrollToElement(fullStageViewButton)
                .moveToElement(fullStageViewButton)
                .pause(2000)
                .perform();

        return this;
    }

    public PipelinePage makeBuilds(int buildsQtt) {
        for (int i = 1; i <= buildsQtt; i++) {
            getWait5().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/build?delay=0sec')]"))).click();

            getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tr[@data-runid='" + i + "']")));
        }
        return this;
    }
}
