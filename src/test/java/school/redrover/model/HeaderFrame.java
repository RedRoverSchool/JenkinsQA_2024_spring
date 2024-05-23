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


    public HeaderFrame(WebDriver driver) {
        super(driver);
    }

    public UserPage clickUserNameOnHeader() {
        userNameOnHeader.click();

        return new UserPage(getDriver());
    }

    public SearchResultPage typeTextToSearchBox(String text) {
        searchBox.sendKeys(text + Keys.ENTER);

        return new SearchResultPage(getDriver());
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
}
