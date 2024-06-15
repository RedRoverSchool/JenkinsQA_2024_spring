package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage<ManageJenkinsPage> {

    @FindBy(css = "[href = 'configure']")
    private WebElement systemLink;

    @FindBy(css = "[href = 'configureTools']")
    private WebElement toolsLink;

    @FindBy(css = "[href = 'pluginManager']")
    private WebElement pluginsLink;

    @FindBy(css = "[href = 'computer']")
    private WebElement nodesLink;

    @FindBy(css = "[href = 'cloud']")
    private WebElement cloudsLink;

    @FindBy(css = "[href = 'appearance']")
    private WebElement appearanceLink;

    @FindBy(css = "[href = 'configureSecurity']")
    private WebElement securityLink;

    @FindBy(css = "[href = 'credentials']")
    private WebElement credentialsLink;

    @FindBy(css = "[href = 'configureCredentials']")
    private WebElement credentialProvidersLink;

    @FindBy(css = "[href = 'securityRealm/']")
    private WebElement usersLink;

    @FindBy(css = "[href = 'systemInfo']")
    private WebElement systemInformationLink;

    @FindBy(css = "[href = 'log']")
    private WebElement systemLogLink;

    @FindBy(css = "[href = 'load-statistics']")
    private WebElement loadStatisticsLink;

    @FindBy(css = "[href = 'about']")
    private WebElement aboutJenkinsLink;

    @FindBy(css = "[href = 'administrativeMonitor/OldData/']")
    private WebElement manageOldDataLink;

    @FindBy(css = "[href = '#']")
    private WebElement reloadConfigurationFromDiskLink;

    @FindBy(css = "[href = 'cli']")
    private WebElement jenkinsCLILink;

    @FindBy(css = "[href = 'script']")
    private WebElement scriptConsoleLink;

    @FindBy(css = "[href = 'prepareShutdown']")
    private WebElement prepareForShutdownLink;

    @FindBy(id = "settings-search-bar")
    private WebElement searchInput;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcut;

    @FindBy(xpath = "//div[@aria-describedby='tippy-6']")
    private WebElement searchHint;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(css = "[class*='search__results__no-results']")
    private WebElement noSearchResultsPopUp;

    @FindBy(css = ".jenkins-search__results a:nth-child(2)")
    private WebElement secondSearchResult;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'System' link")
    public SystemConfigurationPage clickSystemLink() {
        systemLink.click();

        return new SystemConfigurationPage(getDriver());
    }

    @Step("Click 'Tools' link")
    public ToolsConfigurationPage clickToolsLink() {
        toolsLink.click();

        return new ToolsConfigurationPage(getDriver());
    }

    @Step("Click 'Plugins' link")
    public PluginUpdatesPage clickPluginsLink() {
        pluginsLink.click();

        return new PluginUpdatesPage(getDriver());
    }

    @Step("Click 'Nodes' link")
    public NodesTablePage clickNodesLink() {
        nodesLink.click();

        return new NodesTablePage(getDriver());
    }

    @Step("Click 'Clouds' link")
    public CloudsPage clickCloudsLink() {
        cloudsLink.click();

        return new CloudsPage(getDriver());
    }

    @Step("Click 'Appearance' link")
    public AppearancePage clickAppearanceLink() {
        appearanceLink.click();

        return new AppearancePage(getDriver());
    }

    @Step("Click 'Security' link")
    public SecurityPage clickSecurityLink() {
        securityLink.click();

        return new SecurityPage(getDriver());
    }

    @Step("Click 'Credentials' link")
    public CredentialsPage clickCredentialsLink() {
        credentialsLink.click();

        return new CredentialsPage(getDriver());
    }

    @Step("Click 'Credential Providers' link")
    public CredentialProvidersPage clickCredentialProvidersLink() {
        credentialProvidersLink.click();

        return new CredentialProvidersPage(getDriver());
    }

    @Step("Click 'Users' link")
    public UsersPage clickUsersLink() {
        usersLink.click();

        return new UsersPage(getDriver());
    }

    @Step("Click 'System Information' link")
    public SystemInformationPage clickSystemInformationLink() {
        systemInformationLink.click();

        return new SystemInformationPage(getDriver());
    }

    @Step("Click 'System Log' link")
    public LogRecordersPage clickSystemLogLink() {
        systemLogLink.click();

        return new LogRecordersPage(getDriver());
    }

    @Step("Click 'Load Statistics' link")
    public LoadStatisticsPage clickLoadStatisticsLink() {
        loadStatisticsLink.click();

        return new LoadStatisticsPage(getDriver());
    }

    @Step("Click 'About Jenkins' link")
    public AboutJenkinsPage clickAboutJenkinsLink() {
        aboutJenkinsLink.click();

        return new AboutJenkinsPage(getDriver());
    }

    @Step("Click 'Manage Old Data' link")
    public ManageOldDataPage clickManageOldDataLink() {
        manageOldDataLink.click();

        return new ManageOldDataPage(getDriver());
    }

    @Step("Click 'Reload Configuration from Disk' link")
    public ReloadConfigurationDialog clickReloadConfigurationFromDiskLink() {
        reloadConfigurationFromDiskLink.click();

        return new ReloadConfigurationDialog(getDriver());
    }

    @Step("Click 'Jenkins CLI' link")
    public JenkinsCLIPage clickJenkinsCLILink() {
        jenkinsCLILink.click();

        return new JenkinsCLIPage(getDriver());
    }

    @Step("Click 'Script Console' link")
    public JobBuildConsolePage clickScriptConsoleLink() {
        scriptConsoleLink.click();

        return new JobBuildConsolePage(getDriver());
    }

    @Step("Click 'Prepare for Shutdown' link")
    public PrepareForShutdownPage clickPrepareForShutdownLink() {
        prepareForShutdownLink.click();

        return new PrepareForShutdownPage(getDriver());
    }

    @Step("Press slash key")
    public ManageJenkinsPage pressSlashKey() {
        securityLink.sendKeys("/");

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Hover mouse over search input '/' icon")
    public ManageJenkinsPage hoverMouseOverSlashIcon() {
        (new Actions(getDriver()))
                .moveToElement(shortcut)
                .perform();

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Type '{request}' in search input")
    public ManageJenkinsPage typeInSearchInput(String request) {
        searchInput.sendKeys(request);

        return this;
    }

    @Step("Click on second search result in dropdown")
    public <T> T clickSecondSearchResult(T page) {
        getWait2().until(ExpectedConditions.visibilityOf(secondSearchResult)).click();

        return page;
    }

    @Step("Click '{link}' link and get page current URL")
    public String clickManageLinkAndGetCurrentUrl(String link) {
        getDriver().findElement(By.xpath("//dt[text()='" + link + "']")).click();

        return getDriver().getCurrentUrl();
    }

    @Step("Click '{link}' link and get page title")
    public String clickManageLinkAndGetTitle(String link) {
        getDriver().findElement(By.xpath("//dt[text()='" + link + "']")).click();

        return getDriver().getTitle();
    }

    public String getPageHeadingText() {
        return pageHeading.getText();
    }

    public String getSearchInputPlaceholderText() {
        return searchInput.getDomProperty("placeholder");
    }

    public String getLinkDescription(String link) {
        return getDriver().findElement(
                By.xpath("//dt[text()='" + link + "']/following-sibling::dd[1]")).getText();
    }

    public String getNoSearchResultsPopupText() {
        return getWait2().until(ExpectedConditions.visibilityOf(noSearchResultsPopUp)).getText();
    }

    public boolean isSearchInputActive() {
        return searchInput.equals(getDriver().switchTo().activeElement());
    }

    public String getSlashIconTooltipText() {
        return searchHint.getAttribute("tooltip");
    }
}
