package school.redrover.model;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseFrame;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseProjectPage;

public class HeaderFrame extends BaseFrame {

    @FindBy(css = "a.model-link > span")
    private WebElement userNameOnHeader;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = "[data-href$='admin']")
    private WebElement admin;

    @FindBy(css = "[href$='admin/my-views']")
    private WebElement myViewsOnDropDown;

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


    public HeaderFrame(WebDriver driver) {
        super(driver);
    }

    public UserPage clickUserNameOnHeader() {
        userNameOnHeader.click();

        return new UserPage(getDriver());
    }

    public HomePage typeTextToSearchField(String text) {
        searchBox.sendKeys(text);

        return new HomePage(getDriver());
    }

    public SearchResultPage pressEnterOnSearchField() {
        searchBox.sendKeys(Keys.ENTER);

        return new SearchResultPage(getDriver());
    }

    public SearchResultPage typeSearchQueryPressEnter(String searchQuery) {
        searchBox.sendKeys(searchQuery, Keys.ENTER);
        return new SearchResultPage(getDriver());
    }

    public HomePage chooseAndClickFirstSuggestListVariant(){
        getWait5().until(ExpectedConditions.visibilityOf(firstSuggestListVariant)).click();

        return new HomePage(getDriver());
    }

    public String getSearchFieldText() {
        return getWait2().until(ExpectedConditions.visibilityOf(searchFieldText)).getText();
    }

    public <T extends BaseProjectPage<?>> T searchProjectByName(String projectName, T projectType) {
        searchBox.sendKeys(projectName + Keys.ENTER);

        return projectType;
    }

    public void openHeaderUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(admin)
                .pause(1000)
                .click()
                .perform();
    }

    public ViewAllPage clickMyViewsFromDropdown() {
        openHeaderUsernameDropdown();
        myViewsOnDropDown.click();

        return new ViewAllPage(getDriver());
    }

    public <T extends BasePage> T clickWarningIcon(T page) {
        warningIcon.click();

        return page;
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

    public AdminConfigurePage goToAdminConfigurePage() {
        new Actions(getDriver()).moveToElement(adminDropdownChevron)
                .click()
                .perform();
        adminDropdownConfigureLink.click();
        return new AdminConfigurePage(getDriver());
    }
}
