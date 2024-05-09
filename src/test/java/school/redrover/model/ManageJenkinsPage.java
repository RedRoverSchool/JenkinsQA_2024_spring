package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import java.util.List;

public class ManageJenkinsPage extends BasePage {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    @FindBy(className = "jenkins-search__input")
    private WebElement searchInput;

    @FindBy(css = "[href='appearance']")
    private WebElement appearanceButton;

    @FindBy(css = "[href='computer']")
    private WebElement nodesButton;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[5]/div[contains(@class, 'item')]")
    List<WebElement> toolsAndActionsSections;

    @FindBy(css = "[href='securityRealm/']")
    private WebElement usersLink;

    @FindBy(className = "jenkins-search__shortcut")
    private WebElement shortcut;

    @FindBy(xpath = "//div[@aria-describedby='tippy-6']")
    private WebElement searchHint;

    @FindBy(tagName = "h1")
    private WebElement pageTitle;

    @FindBy(css = "[href='#']")
    private WebElement reloadConfigurationFromDiskLink;

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

    public String getPageTitleText() {
        return pageTitle.getText();
    }

    public ReloadConfigurationDialog clickReloadConfigurationFromDisk() {
        reloadConfigurationFromDiskLink.click();

        return new ReloadConfigurationDialog(getDriver());
    }
}