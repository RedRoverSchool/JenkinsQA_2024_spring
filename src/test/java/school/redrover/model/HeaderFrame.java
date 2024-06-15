package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseFrame;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class HeaderFrame<T extends BasePage<T>> extends BaseFrame<T> {

    @FindBy(css = "a.model-link > span")
    private WebElement userNameOnHeader;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = "[data-href$='admin']")
    private WebElement admin;

    @FindBy(css = "[href$='admin/my-views']")
    private WebElement myViewsOnDropdown;

    @FindBy(css = "[class$='am-button security-am']")
    private WebElement warningIcon;

    @FindBy(xpath = "//div[@role='alert']")
    private WebElement warningTooltipLocator;

    @FindBy(xpath = "//a[contains(text(),'Manage Jenkins')]")
    private WebElement manageJenkinsTooltipLink;

    @FindBy(xpath = "//button[@name='configure']")
    private WebElement configureTooltipButton;

    @FindBy(xpath = "//div[@class='yui-ac-bd']//ul//li[1]")
    private WebElement firstSuggestListVariant;

    @FindBy(css = ".yui-ac-bd li:nth-child(1)")
    private WebElement searchFieldText;

    @FindBy(css = "[data-href*='user']")
    private WebElement adminDropdownChevron;

    @FindBy(css = "[href*='configure']")
    private WebElement adminDropdownConfigureLink;

    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logOutIcon;


    public HeaderFrame(WebDriver driver, T returnPage) {
        super(driver, returnPage);
    }

    public UserPage clickUserNameOnHeader() {
        userNameOnHeader.click();

        return new UserPage(getDriver());
    }

    public T typeTextToSearchField(String text) {
        searchBox.sendKeys(text);

        return getReturnPage();
    }

    public SearchResultPage pressEnterOnSearchField() {
        searchBox.sendKeys(Keys.ENTER);

        return new SearchResultPage(getDriver());
    }

    @Step("Type text for search to input field and press 'Enter'")
    public SearchResultPage typeSearchQueryPressEnter(String searchQuery) {
        searchBox.sendKeys(searchQuery, Keys.ENTER);
        return new SearchResultPage(getDriver());
    }

    public T chooseAndClickFirstSuggestListVariant() {
        getWait5().until(ExpectedConditions.visibilityOf(firstSuggestListVariant)).click();

        return getReturnPage();
    }

    public String getSearchFieldText() {
        return getWait2().until(ExpectedConditions.visibilityOf(searchFieldText)).getText();
    }

    public <ProjectPage extends BaseProjectPage<?>> ProjectPage searchProjectByName(String projectName, ProjectPage projectPage) {
        searchBox.sendKeys(projectName + Keys.ENTER);

        return projectPage;
    }

    public void clickChevronForOpenHeaderUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(admin)
                .pause(1000)
                .click()
                .perform();
    }

    @Step("Click 'MyViews' on the header dropdown menu")
    public ViewAllPage clickMyViewsOnHeaderDropdown() {
        clickChevronForOpenHeaderUsernameDropdown();
        myViewsOnDropdown.click();

        return new ViewAllPage(getDriver());
    }

    public T clickWarningIcon() {
        warningIcon.click();

        return getReturnPage();
    }

    public String getWarningTooltipText() {
        return getWait5().until(ExpectedConditions.visibilityOf(warningTooltipLocator)).getText();
    }

    public ManageJenkinsPage clickManageJenkinsTooltipLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(manageJenkinsTooltipLink)).click();

        return new ManageJenkinsPage(getDriver());
    }

    public SecurityPage clickConfigureTooltipButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureTooltipButton)).click();

        return new SecurityPage(getDriver());
    }

    public AdminConfigurePage goToAdminConfigurePage() {
        new Actions(getDriver()).moveToElement(adminDropdownChevron)
                .click()
                .perform();
        adminDropdownConfigureLink.click();
        return new AdminConfigurePage(getDriver());
    }

    public SignInToJenkinsPage clickLogOut() {
        logOutIcon.click();

        return new SignInToJenkinsPage(getDriver());
    }
}
