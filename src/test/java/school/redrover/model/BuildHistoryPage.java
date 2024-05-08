package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    @FindBy(css = "td [class$='link'] [class$='dropdown-chevron']")
    private WebElement buildHistoryItemDropdownArrow;

    @FindBy(css = "[href$='Delete']")
    private WebElement dropdownDeleteButton;

    @FindBy(css = "td [class$='link'][href*='job']")
    private List<WebElement> buildsList;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getBuildHistoryPageUrl() {
        return TestUtils.getBaseUrl() + "/view/all/builds";
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

}
