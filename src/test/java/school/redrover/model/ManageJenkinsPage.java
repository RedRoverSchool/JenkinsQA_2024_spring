package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import java.util.List;

public class ManageJenkinsPage extends BasePage {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    @FindBy(id = "settings-search-bar")
    private WebElement searchInput;

    @FindBy(css = "[href='appearance']")
    private WebElement appearanceButton;

    @FindBy(css = "[href='computer']")
    private WebElement nodesButton;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]/div[contains(@class, 'item')]")
    private List<WebElement> toolsAndActionsSections;

    @FindBy(css = "[href='securityRealm/']")
    private WebElement usersLink;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcut;

    @FindBy(xpath = "//div[@aria-describedby='tippy-6']")
    private WebElement searchHint;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(css = "[href='#']")
    private WebElement reloadConfigurationFromDiskLink;

    @FindBy(css = "[class*='search__results__no-results']")
    private WebElement noSearchResultsPopUp;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public SecurityPage clickSecurity() {
        securityLink.click();

        return new SecurityPage(getDriver());
    }
  
    public String getManageJenkinsPage() {
        return TestUtils.getBaseUrl() + "/manage/";
    }

    public boolean isSearchInputDisplayed() {
        return searchInput.isDisplayed();
    }

    public AppearancePage clickAppearanceButton() {
        appearanceButton.click();

        return new AppearancePage(getDriver());
    }

    public boolean areToolsAndActionsSectionsEnabled() {
        return areElementsEnabled(toolsAndActionsSections);
    }

    public UsersPage clickUsers() {
        usersLink.click();

        return new UsersPage(getDriver());
    }

    public boolean isShortcutDisplayed() {
        return shortcut.isDisplayed();
    }

    public boolean isSearchFieldActivateElement() {
        return searchInput.equals(getDriver().switchTo().activeElement());
    }

    public boolean isSearchHintDisplayed() {
        return searchHint.isDisplayed();
    }

    public ManageJenkinsPage pressSlashKey() {
        securityLink.sendKeys("/");

        return new ManageJenkinsPage(getDriver());
    }

    public ManageJenkinsPage hoverMouseOverTheTooltip() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(shortcut);
        actions.perform();

        return new ManageJenkinsPage(getDriver());
    }

    public String getSearchHintText() {
        return searchHint.getAttribute("tooltip");
    }

    public NodesTablePage clickNodes() {
        nodesButton.click();

        return new NodesTablePage(getDriver());
    }

    public String getPageHeadingText() {
        return pageHeading.getText();
    }

    public ReloadConfigurationDialog clickReloadConfigurationFromDisk() {
        reloadConfigurationFromDiskLink.click();

        return new ReloadConfigurationDialog(getDriver());
    }

    public String getSearchInputPlaceholderText() {
        return searchInput.getDomProperty("placeholder");
    }

    public ManageJenkinsPage typeSearchSettingsRequest(String request) {
        searchInput.sendKeys(request);

        return this;
    }

    public String getNoSearchResultsPopUpText() {
        return getWait2().until(ExpectedConditions.visibilityOf(noSearchResultsPopUp)).getText();
    }
}