package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends BasePage<HomePage> {

    @FindBy(xpath = "//a[.='New Item']")
    private WebElement newItem;

    @FindBy(linkText = "Create a job")
    private WebElement createAJobLink;

    @FindBy(css = "[href='/computer/']")
    private WebElement buildExecutorStatusLink;

    @FindBy(css = "#executors tr [href]")
    private List<WebElement> nodesList;

    @FindBy(css = "[href='/manage']")
    private WebElement manageJenkinsLink;

    @FindBy(css = "[href='/newView']")
    private WebElement newView;

    @FindBy(css = "[href*='rename']")
    private WebElement renameFromDropdown;

    @FindBy(css = "[href*='move']")
    private WebElement moveFromDropdown;

    @FindBy(xpath = "//a[@class='sortheader' and text()='Name']")
    private WebElement columnNameTitle;

    @FindBy(css = "tr[id*='job_'] > td > div > svg")
    private WebElement projectIcon;

    @FindBy(css = "div.jenkins-icon-size__items.jenkins-buttons-row > ol > li")
    private List<WebElement> sizeIcon;

    @FindBy(css = "a[href $= '/move']")
    private WebElement dropdownMove;

    @FindBy(css = "div#breadcrumbBar a[href = '/']")
    private WebElement dashboardBreadcrumbs;

    @FindBy(css = "[class='tippy-box'] [href='/manage']")
    private WebElement manageFromDashboardBreadcrumbsMenu;

    @FindBy(css = "[aria-describedby*='tippy']")
    private WebElement buildSchedulePopUp;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewOnDropdown;

    @FindBy(css = ".tab.active a")
    private WebElement activeViewName;

    @FindBy(css = ".tab input:not(:checked)~a")
    private WebElement passiveViewName;

    @FindBy(css = "[href$='builds']")
    private WebElement buildHistoryButton;

    @FindBy(xpath = "//a[contains(@href, '/move')]")
    private WebElement moveOption;

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement peopleButton;

    @FindBy(css = "button[href $= '/doDelete']")
    private WebElement dropdownDelete;

    @FindBy(css = "[href$='pipeline-syntax']")
    private WebElement dropdownPipelineSyntax;

    @FindBy(xpath = "//div[@class='tabBar']/div")
    private List<WebElement> viewNameList;

    @FindBy(xpath = "//td[@class='jenkins-table__cell--tight']//a[contains(@tooltip,'Schedule')]")
    private WebElement greenBuildArrow;

    @FindBy(css = ".disabledJob a")
    private List<WebElement> disabledProjectList;

    @FindBy(xpath = "//a[@href='/me/my-views']")
    private WebElement myViewsOnSidebar;

    @FindBy(css = "#description-link")
    private WebElement editDescriptionLink;

    @FindBy(css = "[name='description']")
    private WebElement descriptionTextarea;

    @FindBy(css = "[name='Submit']")
    private WebElement saveButton;

    @FindBy(css = "#description > *:first-child")
    private WebElement descriptionText;

    @FindBy(css = "#tasks > div")
    private List<WebElement> sidebarMenuList;

    @FindBy(xpath = "//td[@class='jenkins-table__cell--tight']//span[@data-notification='Build scheduled']")
    private WebElement buildScheduledMessagePopUp;

    @FindBy(css = "#notification-bar > span")
    private WebElement buildDoneGreenMessage;

    @FindBy(css = "button[href $= '/build?delay=0sec']")
    private WebElement dropdownBuild;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(css = "#executors")
    private WebElement executors;

    @FindBy(css = "[href='/toggleCollapse?paneId=executors']")
    private WebElement toggleCollapse;

    @FindBy(css = "tbody [tooltip]")
    private WebElement statusIcon;

    @FindBy(css = "tr > td > .jenkins-table__link > span:first-child")
    private List<WebElement> itemList;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'New Item' on sidebar menu")
    public CreateNewItemPage clickNewItem() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    @Step("Click 'Create a Job'")
    public CreateNewItemPage clickCreateAJob() {
        createAJobLink.click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemList() {
        return itemList.stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Click project dropdown menu")
    public HomePage openItemDropdown(String projectName) {
        WebElement element = getDriver().findElement(By.cssSelector(String.format(
                "td>a[href = 'job/%s/']",
                TestUtils.asURL(projectName))));
        openElementDropdown(element);

        return this;
    }

    @Step("Click 'Delete' in dropdown menu")
    public DeleteDialog clickDeleteInDropdown(DeleteDialog dialog) {
        dropdownDelete.click();
        return dialog;
    }

    @Step("Click 'Rename' on dropdown menu")
    public FreestyleRenamePage clickRenameOnDropdownForFreestyleProject() {
        renameFromDropdown.click();

        return new FreestyleRenamePage(getDriver());
    }

    @Step("Click 'Move' in project dropdown menu")
    public MovePage clickMoveInDropdown() {
        dropdownMove.click();
        return new MovePage(getDriver());
    }

    @Step("Click on the link 'Build Executor Status'")
    public NodesTablePage clickBuildExecutorStatusLink() {
        buildExecutorStatusLink.click();

        return new NodesTablePage(getDriver());
    }

    public List<String> getNodesList() {
        return nodesList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isNodeDisplayed(String name) {
        return getDriver().findElement(By.cssSelector("[href='/computer/" + name + "/']")).isDisplayed();
    }

    @Step("Click on the specific Multi-configuration project name")
    public MultiConfigurationProjectPage clickSpecificMultiConfigurationProjectName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    @Step("Click on the 'Manage Jenkins' in the sidebar menu")
    public ManageJenkinsPage clickManageJenkins() {
        manageJenkinsLink.click();

        return new ManageJenkinsPage(getDriver());
    }

    public CreateNewViewPage clickPlusToCreateView() {
        newView.click();

        return new CreateNewViewPage(getDriver());
    }

    public ViewPage clickViewName(String viewName) {
        getDriver().findElement(By.linkText(viewName)).click();

        return new ViewPage(getDriver());
    }

    @Step("Open dropdown menu of the Project")
    public HomePage openItemDropdownWithSelenium(String projectName) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(
                        By.xpath("//a[@href='job/" + TestUtils.asURL(projectName) + "/']")))
                .pause(1000)
                .scrollToElement(getDriver().findElement(
                        By.cssSelector(String.format("[data-href*='/job/%s/']", TestUtils.asURL(projectName)))))
                .click()
                .perform();

        return this;
    }

    public MultiConfigurationRenamePage clickRenameOnDropdownForMultiConfigurationProject() {
        renameFromDropdown.click();

        return new MultiConfigurationRenamePage(getDriver());
    }

    public MultiConfigurationMovePage selectMoveFromDropdown() {
        moveFromDropdown.click();

        return new MultiConfigurationMovePage(getDriver());
    }

    @Step("Click on the specific Pipeline name")
    public PipelineProjectPage clickSpecificPipelineName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new PipelineProjectPage(getDriver());
    }

    public boolean isItemDeleted(String name) {
        return !getItemList().contains(name);
    }

    public boolean isItemExists(String name) {
        return getItemList().contains(name);
    }

    @Step("Click on the specific Multibranch Pipeline name")
    public MultibranchPipelineProjectPage clickSpecificMultibranchPipelineName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public List<String> getDropdownMenu() {

        return Arrays.stream(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[@class='jenkins-dropdown']")))
                        .getText()
                        .split("\\r?\\n"))
                .toList();
    }

    public HomePage clickTitleForSortByName() {
        columnNameTitle.click();
        return new HomePage(getDriver());
    }

    public HomePage clickIconForChangeSize(int i) {
        sizeIcon.get(i).click();

        return this;
    }

    public int getProjectIconHeight() {
        return projectIcon.getSize().height;
    }

    @Step("Click on the specific Organization Folder name")
    public OrganizationFolderProjectPage clickSpecificOrganizationFolderName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new OrganizationFolderProjectPage(getDriver());
    }

    @Step("Click on Chevron of the Dashboard")
    public HomePage openDashboardBreadcrumbsDropdown() {
        WebElement chevron = dashboardBreadcrumbs.findElement(By.cssSelector("[class$='chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));"
                        + "arguments[0].dispatchEvent(new Event('click'));",
                chevron);

        return this;
    }

    @Step("Click on Manage Jenkins in the Dashboard dropdown menu")
    public ManageJenkinsPage clickManageFromDashboardBreadcrumbsMenu() {
        manageFromDashboardBreadcrumbsMenu.click();

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Click on 'Full Stage View' on Item dropdown menu")
    public FullStageViewPage clickFullStageViewOnDropdown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(fullStageViewOnDropdown)).click();

        return new FullStageViewPage(getDriver());
    }

    @Step("Click 'Rename' on dropdown menu")
    public MultibranchPipelineRenamePage clickRenameOnDropdownForMultibranchPipeline() {
        renameFromDropdown.click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    @Step("Click the '{name}' project name")
    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath(
                "//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();
        return page;
    }

    public HomePage moveMouseToPassiveViewName() {
        new Actions(getDriver())
                .moveToElement(passiveViewName)
                .pause(200)
                .perform();
        return this;
    }

    public HomePage mouseClick() {
        new Actions(getDriver())
                .click()
                .perform();
        return this;
    }

    public String getColorOfPassiveViewNameBackground() {
        return passiveViewName.getCssValue("background-color");
    }

    public String getColorOfActiveViewNameBackground() {
        return activeViewName.getCssValue("background-color");
    }

    @Step("Click green triangle to schedule build for project")
    public HomePage scheduleBuildForItemAndWaitForBuildSchedulePopUP(String itemName) {
        getDriver().findElement(By.xpath("//a[contains(@tooltip,'Schedule a Build for " + itemName + "')]")).click();
        getWait2().until(ExpectedConditions.visibilityOf(buildSchedulePopUp));

        return this;
    }

    @Step("Click 'Build History' on sidebar menu")
    public BuildHistoryPage clickBuildHistory() {
        buildHistoryButton.click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Select folder to move")
    public MovePage chooseFolderToMove() {
        getWait5().until(ExpectedConditions.visibilityOf(moveOption)).click();

        return new MovePage(getDriver());
    }

    @Step("Click 'People' on sidebar")
    public PeoplePage clickPeopleOnSidebar() {
        peopleButton.click();

        return new PeoplePage(getDriver());
    }

    @Step("Click '{itemName}' Folder name")
    public FolderProjectPage clickSpecificFolderName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new FolderProjectPage(getDriver());
    }

    @Step("Click '{itemName}' Freestyle project name")
    public FreestyleProjectPage clickSpecificFreestyleProjectName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new FreestyleProjectPage(getDriver());
    }

    @Step("Click 'Rename' on dropdown menu for the folder")
    public FolderRenamePage clickRenameOnDropdownForFolder() {
        renameFromDropdown.click();

        return new FolderRenamePage(getDriver());
    }

    @Step("Click 'Pipeline Syntax' from dropdown menu")
    public PipelineSyntaxPage openItemPipelineSyntaxFromDropdown() {
        dropdownPipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }

    public int getSizeViewNameList() {
        return viewNameList.size();
    }

    @Step("Click 'Delete' in dropdown menu and confirm it by click 'Yes' in confirming dialog ")
    public HomePage clickDeleteOnDropdownAndConfirm() {
        dropdownDelete.click();

        getWait5().until(ExpectedConditions.visibilityOf(yesButton)).click();

        return this;
    }

    @Step("Get Tooltip of green Build arrow")
    public String getBuildStatus() {
        return greenBuildArrow.getAttribute("tooltip");
    }

    public List<String> getDisabledProjectListText() {
        return disabledProjectList.stream().map(WebElement::getText).toList();
    }

    public MyViewsPage clickMyViewsOnSidebar() {
        myViewsOnSidebar.click();

        return new MyViewsPage(getDriver());
    }

    public HomePage clickEditDescription() {
        editDescriptionLink.click();

        return this;
    }

    public HomePage typeDescription(String text) {
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(text);

        return this;
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public String getEditDescriptionLinkText() {
        return editDescriptionLink.getText();
    }

    private WebElement getTooltipLocator(String tooltipText) {
        return getDriver().findElement(By.cssSelector("a[tooltip='Help for feature: " + tooltipText + "']"));
    }

    public boolean isTooltipDisplayed(String tooltipText) {
        WebElement tooltip = getTooltipLocator(tooltipText);
        hoverOverElement(tooltip);

        return tooltip.isDisplayed();
    }

    public List<String> getSidebarMenuList() {
        List<String> menuList = new ArrayList<>();
        for (WebElement element : sidebarMenuList) {
            menuList.add(element.getText());
        }

        return menuList;
    }

    @Step("Checking for a button of Schedule a Build for the Pipeline")
    public boolean isButtonOfScheduleABuildExist(String projectName) {
        int num = getDriver().findElements(By.xpath(
                "//table//a[@title= 'Schedule a Build for " + projectName + "']")).size();

        return num != 0;
    }

    public HomePage clickGreenBuildArrowButton() {
        greenBuildArrow.click();

        return this;
    }

    public String getBuildScheduledMessage() {
        return buildScheduledMessagePopUp.getAttribute("data-notification");
    }

    public HomePage clickBuildNowFromDropdown() {
        dropdownBuild.click();

        return this;
    }

    public String catchBuildNowDoneMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(buildDoneGreenMessage)).getText();

    }

    public boolean isNodesDisplayedOnExecutorsPanel() {
        return executors.getText().contains("built-in node");
    }

    public void clickOnExecutorPanelToggle() {
        toggleCollapse.click();
    }

    @Step("Get status icon tooltip")
    public String getStatusIconTooltip() {
        return statusIcon.getAttribute("tooltip");
    }

}
