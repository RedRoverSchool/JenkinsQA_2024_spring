package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage<BuildHistoryPage> {

    @FindBy(css = "td [class$='link'] [class$='dropdown-chevron']")
    private WebElement buildHistoryItemDropdownArrow;

    @FindBy(css = "[href$='Delete']")
    private WebElement dropdownDelete;

    @FindBy(css = "td [class$='link'][href*='job']")
    private List<WebElement> buildsList;

    @FindBy(css = "[class*='_button'] [href$='/1/console']")
    private WebElement buildConsole;

    @FindBy(xpath = "//a[@class='jenkins-table__link model-link']")
    private WebElement buildNameOnTimeline;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinkList;

    @FindBy(css = "dialog .jenkins-button--primary")
    private WebElement yesButton;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click Project arrow to open dropdown menu")
    public BuildHistoryPage clickItemDropdownArrow(String name) {
        hoverOverElement(getDriver().findElement(By.cssSelector("td [href='/job/" + name + "/']")));
        clickBreadcrumbsDropdownArrow(buildHistoryItemDropdownArrow);

        return this;
    }

    public BuildHistoryPage clickDeleteOnDropdown() {
        dropdownDelete.click();

        return this;
    }

    public BuildHistoryPage clickYesToConfirmDelete() {
        yesButton.click();

        return this;
    }

    @Step("Get Builds list")
    public List<String> getBuildsList() {
        return buildsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isBuildDeleted(String name) {
        return !getBuildsList().contains(name);
    }

    @Step("Click console for build #1")
    public BuildConsoleOutputPage clickBuild1Console() {
        buildConsole.click();

        return new BuildConsoleOutputPage(getDriver());
    }

    @Step("Check displayed build on timeline")
    public boolean isDisplayedBuildOnTimeline() {
        return buildNameOnTimeline.isDisplayed();
    }

    @Step("Get permalink list")
    public List<String> getPermalinkList() {

        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinkList))
                .stream()
                .map(WebElement::getText)
                .map(permalink -> permalink.split(",")[0].trim())
                .toList();
    }

}


