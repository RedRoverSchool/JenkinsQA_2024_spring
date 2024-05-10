package school.redrover.model;

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
import java.util.List;


public class HomePage extends BasePage {

    @FindBy(linkText = "Create a job")
    private WebElement createAJobLink;

    @FindBy(css = "[href='/computer/']")
    private WebElement nodesLink;

    @FindBy(css = "#executors tr [href]")
    private List<WebElement> nodesList;

    @FindBy(css = "td > a[href^='job']")
    private WebElement pipelineItem;

    @FindBy(css = "[href='/manage']")
    private WebElement manageJenkinsLink;

    @FindBy(css = "[tooltip='New View']")
    private WebElement newView;

    @FindBy(css = "[href*='rename']")
    private WebElement renameFromDropdown;

    @FindBy(css = "div.jenkins-dropdown")
    private WebElement dropdownMenu;

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

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    @FindBy(css = "[class$='jenkins_ver']")
    private WebElement jenkinsFooter;

    @FindBy(xpath = "//div/a[@href='/manage/about']")
    private WebElement aboutJenkinsDropdownItem;

    @FindBy(xpath = "//div/a[@href='https://www.jenkins.io/participate/']")
    private WebElement involvedDropdownItem;

    @FindBy(xpath = "//div/a[@href='https://www.jenkins.io/']")
    private WebElement websiteDropdownItem;

    @FindBy(css =  "a.jenkins-table__link.model-link.inside")
    private List<WebElement> allExistingJobs;

    @FindBy(css = ".tab.active a")
    private WebElement activeViewName;

    @FindBy(css = ".tab input:not(:checked)~a")
    private WebElement passiveViewName;

    @FindBy(css = "[href$='builds']")
    private WebElement buildHistoryButton;

    @FindBy(xpath = "//*[@class=' job-status-']/td[3]/a")
    private WebElement createdElementInTable;

    @FindBy(css = "[class$=jenkins_ver]")
    private WebElement version;

    @FindBy(className = "jenkins-dropdown__item")
    private List<WebElement> dropDownElements;

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(xpath = "//a[@class='jenkins-table__link model-link inside']")
    private List<WebElement> listNamesOfItems;

    @FindBy(xpath = "//td//button[@class='jenkins-menu-dropdown-chevron']")
    private List<WebElement> jenkinsMenuDropdownChevron;

    @FindBy(xpath = "//a[contains(@href, '/move')]")
    private WebElement moveOption;

    @FindBy (xpath = "//*[@href='newJob']")
    private WebElement createJob;

    @FindBy(css = "[class$='am-button security-am']")
    private WebElement warningIcon;

    @FindBy(xpath = "//div[@role='alert']")
    private WebElement warningTooltipLocator;

    @FindBy(xpath = "//a[contains(text(),'Manage Jenkins')]")
    private WebElement manageJenkinsTooltipLink;

    @FindBy(xpath = "//button[@name='configure']")
    private WebElement configureTooltipButton;

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement peopleButton;

    @FindBy(css = "button[href $= '/doDelete']")
    private WebElement dropdownDelete;

    @FindBy(css = "[href$='pipeline-syntax']")
    private WebElement dropdownPipelineSyntax;

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

    public FolderProjectPage clickFolder(String name) {
        getDriver().findElement(By.xpath("//a[.='" + name + "']")).click();

        return new FolderProjectPage(getDriver());
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

    public MovePage clickMoveInDropdown() {
        dropdownMove.click();
        return new MovePage(getDriver());
    }

    public NodesTablePage clickNodesLink() {
        nodesLink.click();

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

    public MultiConfigurationProjectPage clickMCPName(String projectName) {
        getDriver().findElement(By.cssSelector(String.format("[href = 'job/%s/']", projectName))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public PipelineProjectPage clickCreatedPipelineName() {
        pipelineItem.click();

        return new PipelineProjectPage(getDriver());
    }

    public ViewAllPage clickMyViewsFromDropdown() {
        openHeaderUsernameDropdown();
        getDriver().findElement(By.cssSelector("[href$='admin/my-views']")).click();

        return new ViewAllPage(getDriver());
    }

    public ManageJenkinsPage clickManageJenkins() {
        manageJenkinsLink.click();

        return new ManageJenkinsPage(getDriver());
    }

    public CreateNewViewPage clickNewView() {
        newView.click();

        return new CreateNewViewPage(getDriver());
    }

    public ViewPage clickViewName(String viewName) {
        getDriver().findElement(By.linkText(viewName)).click();

        return new ViewPage(getDriver());
    }

    public FolderProjectPage clickOnCreatedFolder(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='job_" + name + "']/td/a"))).click();

        return new FolderProjectPage(getDriver());
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

       public MultiConfigurationConfirmRenamePage selectRenameFromDropdown() {
        renameFromDropdown.click();

        return new MultiConfigurationConfirmRenamePage(getDriver());
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

    public MultibranchPipelineProjectPage clickMPName(String projectName) {
        getDriver().findElement(By.cssSelector(String.format("[href = 'job/%s/']", projectName))).click();

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public WebElement getDropdownMenu() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown']")));
    }

    public HomePage clickColumnNameTitle() {
        columnNameTitle.click();
        return new HomePage(getDriver());
    }

    public HomePage clickSizeIcon(int i) {
        sizeIcon.get(i).click();

        return this;
    }

    public int getProjectIconHeight() {
        return projectIcon.getSize().height;
    }

    public PipelineProjectPage chooseCreatedProject(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/"
                + projectName.replaceAll(" ", "%20") + "/']"))).click();

        return new PipelineProjectPage(getDriver());
    }
    public FreestyleProjectPage chooseCreatedFreestyleProject(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/"
                + projectName.replaceAll(" ", "%20") + "/']"))).click();

        return new FreestyleProjectPage(getDriver());
    }
    public HomePage openDashboardBreadcrumbsDropdown() {
        WebElement chevron = dashboardBreadcrumbs.findElement(By.cssSelector("[class$='chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));" +
                        "arguments[0].dispatchEvent(new Event('click'));",
                chevron);

        return this;
    }

    public CreateNewItemPage clickNewJobFromDashboardBreadcrumbsMenu() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tippy-box a[href $= 'newJob']"))).click();

        return new CreateNewItemPage(getDriver());
    }

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

    public HomePage jenkinsFooterClick() {
        jenkinsFooter.click();

        return this;
    }

    public AboutJenkinsPage selectAboutJenkins() {
        getWait5().until(ExpectedConditions.elementToBeClickable(aboutJenkinsDropdownItem)).click();

        return new AboutJenkinsPage(getDriver());
    }

    public FullStageViewPage clickFullStageViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(fullStageViewButton)).click();

        return new FullStageViewPage(getDriver());
    }

    public List<String> allExistingJobsNames() {
        return allExistingJobs
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getJobsBeginningFromThisFirstLetters(String firstLetters) {
        return allExistingJobs
                .stream()
                .map(WebElement::getText)
                .toList()
                .stream()
                .filter(el-> el.substring(0,firstLetters.length()).equalsIgnoreCase(firstLetters))
                .toList();
    }

    public MultibranchPipelineRenamePage clickRenameFromDropdownMP() {
        renameFromDropdown.click();

        return new MultibranchPipelineRenamePage(getDriver());
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath(
                "//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();
        return page;
    }

    public boolean isDisplayedAboutJenkinsDropdownItem() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(aboutJenkinsDropdownItem)).isDisplayed();
    }

    public boolean isDisplayedInvolvedDropdownItem() {
        return involvedDropdownItem.isDisplayed();
    }

    public boolean isDisplayedWebsiteDropdownItem() {
        return websiteDropdownItem.isDisplayed();
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
        getDriver().findElement(By.cssSelector("td [title='Schedule a Build for " +
                itemName.replace(" ", "%20") + "']")).click();

        return this;
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistoryButton.click();

        return new BuildHistoryPage(getDriver());
    }

    public FolderProjectPage clickFolderName() {
        createdElementInTable.click();

        return new FolderProjectPage(getDriver());
    }

    public HomePage clickVersion() {
        version.click();

        return this;
    }

    public List<String> getVersionDropDownElementsValues(){
        List<String> actualDropDownElementsValues = new ArrayList<>();
        for (WebElement element : dropDownElements) {
            actualDropDownElementsValues.add(element.getDomProperty("innerText"));
        }
        return actualDropDownElementsValues;
    }

    public List<WebElement> getTheListOfFreestyleProjects(String freestyleProjectName){
        return getDriver().findElements(
                By.xpath("//span[text() = '" + freestyleProjectName + "']"));
    }

    public String getHeadingValue() {

        return heading.getText();
    }
    public HomePage createNewFolder(String folderName) {
        clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();
        return new HomePage(getDriver());
    }

    public MovePage chooseFolderToMove() {
        getWait5().until(ExpectedConditions.visibilityOf(moveOption)).click();
        return new MovePage(getDriver());
    }

    public HomePage clickWarningIcon() {
        warningIcon.click();
        return this;
    }

    public String getWarningTooltipText() {
        WebElement warningTooltipText = getWait5().until(ExpectedConditions.visibilityOf(warningTooltipLocator));
        return warningTooltipText.getText();
    }

    public ManageJenkinsPage clickManageJenkinsTooltipLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(manageJenkinsTooltipLink)).click();
        return new ManageJenkinsPage(getDriver());
    }

    public SecurityPage clickConfigureTooltipButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureTooltipButton)).click();
        return new SecurityPage(getDriver());
    }

    public PeoplePage clickPeopleButton() {
        peopleButton.click();

        return new PeoplePage(getDriver());
    }
    public FolderProjectPage clickSpecificFolderName(String itemName) {
        getDriver().findElement(
                By.cssSelector("td>[href^='job/" + itemName.replace(" ", "%20") + "']")).click();

        return new FolderProjectPage(getDriver());
    }

    public FolderRenamePage renameFolderFromDropdown() {
        renameFromDropdown.click();

        return new FolderRenamePage(getDriver());
    }

    public  CreateNewItemPage clickCreateJob() {
        createJob.click();

        return new CreateNewItemPage(getDriver());
    }

    public PipelineSyntaxPage openItemPipelineSyntaxFromDropdown() {
        dropdownPipelineSyntax.click();

        return new PipelineSyntaxPage(getDriver());
    }
}
