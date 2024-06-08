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

    @FindBy(id = "executors")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//td[text()='Idle']")
    private List<WebElement> buildExecutorStatusList;

    @FindBy(css = "[aria-describedby*='tippy']")
    private WebElement builSchedulePopUp;

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

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


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage clickCreateAJob() {
        createAJobLink.click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemList() {
        return getDriver().findElements(By.cssSelector("tr > td > .jenkins-table__link > span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public HomePage openItemDropdown(String projectName) {
        WebElement element = getDriver().findElement(By.cssSelector(String.format(
                "td>a[href = 'job/%s/']",
                TestUtils.asURL(projectName))));
        openElementDropdown(element);
        return this;
    }

    public DeleteDialog clickDeleteInDropdown(DeleteDialog dialog) {
        dropdownDelete.click();
        return dialog;
    }

    public FreestyleRenamePage clickRenameOnDropdownForFreestyleProject() {
        renameFromDropdown.click();

        return new FreestyleRenamePage(getDriver());
    }

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

    public OrganizationFolderProjectPage clickSpecificOrganizationFolderName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new OrganizationFolderProjectPage(getDriver());
    }

    @Step("Click on Chevron of the Dashboard")
    public HomePage openDashboardBreadcrumbsDropdown() {
        WebElement chevron = dashboardBreadcrumbs.findElement(By.cssSelector("[class$='chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));" +
                        "arguments[0].dispatchEvent(new Event('click'));",
                chevron);

        return this;
    }

    @Step("Click on Manage Jenkins in the Dashboard dropdown menu")
    public ManageJenkinsPage clickManageFromDashboardBreadcrumbsMenu() {
        manageFromDashboardBreadcrumbsMenu.click();

        return new ManageJenkinsPage(getDriver());
    }

    public String getBuildExecutorStatusText() {
        return buildExecutorStatus.getText();
    }

    public List<WebElement> getBuildExecutorStatusList() {
        return buildExecutorStatusList.stream().toList();
    }

    public int getBuildExecutorListSize() {
        return buildExecutorStatusList.size();
    }

    public FullStageViewPage clickFullStageViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(fullStageViewButton)).click();

        return new FullStageViewPage(getDriver());
    }

    public MultibranchPipelineRenamePage clickRenameOnDropdownForMultibranchPipeline() {
        renameFromDropdown.click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

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

    public String getPassiveViewNameBackgroundColor() {
        return passiveViewName.getCssValue("background-color");
    }

    public String getActiveViewNameBackgroundColor() {
        return activeViewName.getCssValue("background-color");
    }

    public HomePage scheduleBuildForItem(String itemName) {
        getDriver().findElement(By.xpath("//a[contains(@tooltip,'Schedule a Build for " + itemName + "')]")).click();

        return this;
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistoryButton.click();

        return new BuildHistoryPage(getDriver());
    }

    public HomePage waitForBuildSchedulePopUp() {
        getWait2().until(ExpectedConditions.visibilityOf(builSchedulePopUp));

        return this;
    }

    public MovePage chooseFolderToMove() {
        getWait5().until(ExpectedConditions.visibilityOf(moveOption)).click();

        return new MovePage(getDriver());
    }

    public PeoplePage clickPeopleOnSidebar() {
        peopleButton.click();

        return new PeoplePage(getDriver());
    }

    public FolderProjectPage clickSpecificFolderName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new FolderProjectPage(getDriver());
    }

    public FreestyleProjectPage clickSpecificFreestyleProjectName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new FreestyleProjectPage(getDriver());
    }

    public FolderRenamePage clickRenameOnDropdownForFolder() {
        renameFromDropdown.click();

        return new FolderRenamePage(getDriver());
    }

    public PipelineSyntaxPage openItemPipelineSyntaxFromDropdown() {
        dropdownPipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }

    public int getSizeViewNameList() {

        return viewNameList.size();
    }

    public HomePage clickDeleteOnDropdownAndConfirm() {
        dropdownDelete.click();
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();

        return this;
    }

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

    public int getBuildButtonCountForProject(String projetcName) {
        return getDriver().findElements(By.xpath("//table//a[@title= 'Schedule a Build for " + projetcName + "']")).size();
    }

}
