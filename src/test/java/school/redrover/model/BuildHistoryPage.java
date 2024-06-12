package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import java.util.List;
import java.util.stream.Collectors;

public class BuildHistoryPage extends BasePage<BuildHistoryPage> {

    @FindBy(css = "td [class$='link'] [class$='dropdown-chevron']")
    private WebElement buildHistoryItemDropdownArrow;

    @FindBy(css = "[href$='Delete']")
    private WebElement dropdownDeleteButton;

    @FindBy(css = "td [class$='link'][href*='job']")
    private List<WebElement> buildsList;

    @FindBy(css = "[class*='_button'] [href$='/1/console']")
    private WebElement buildConsole;

    @FindBy(xpath = "//a[@class='jenkins-table__link model-link']")
    private WebElement buildNameOnTimeline;

    @FindBy(className = "console-output")
    private WebElement TextConsoleOutput;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinkList;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public BuildHistoryPage hoverOverItemName(String name) {
        hoverOverElement(getDriver().findElement(By.cssSelector("td [href='/job/" + name + "/']")));

        return this;
    }

    public BuildHistoryPage clickItemDropdownArrow() {
        clickSpecificDropdownArrow(buildHistoryItemDropdownArrow);

        return this;
    }

    public DeleteDialog clickItemDeleteButton() {
        dropdownDeleteButton.click();

        return new DeleteDialog(getDriver());
    }

    public List<String> getBuildsList() {
        return buildsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isBuildDeleted(String name) {
        return !getBuildsList().contains(name);
    }

    public BuildConsoleOutputPage clickBuild1Console() {
        buildConsole.click();

        return new BuildConsoleOutputPage(getDriver());
    }

    public boolean isDisplayedBuildOnTimeline() {
        return buildNameOnTimeline.isDisplayed();
    }

    public List<String> getPermalinkList() {

        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinkList))
                .stream()
                .map(WebElement::getText)
                .map(permalink -> permalink.split(",")[0].trim())
                .collect(Collectors.toList());
    }

}


