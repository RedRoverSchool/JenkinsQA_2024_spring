package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PeoplePage extends BasePage {

    @FindBy(css = "tr>td:nth-child(2)")
    private List<WebElement> userIDList;

    @FindBy(css = "[title='Small']")
    private WebElement smallIconButton;

    @FindBy(css = "[tooltip='Medium']")
    private WebElement mediumIconButton;

    @FindBy(css = "[title='Large']")
    private WebElement largeIconButton;

    @FindBy(css = "[id*='person-admin'] svg")
    private WebElement userTableIcon;

    @FindBy(css = "h1")
    private WebElement pageHeading;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public String getPeoplePageUrl() {
        return TestUtils.getBaseUrl() + "/asynchPeople/";
    }

    public UserPage clickUser(String userID) {
        getDriver().findElement(By.cssSelector("td > [href*='" + userID.toLowerCase() + "']")).click();
        return new UserPage(getDriver());
    }

    public PeoplePage clickSmallIconButton() {
        smallIconButton.click();
        return this;
    }

    public PeoplePage clickMediumIconButton() {
        mediumIconButton.click();
        return this;
    }

    public PeoplePage clickLargeIconButton() {
        largeIconButton.click();
        return this;
    }

    public Dimension getUserIconSize() {
        return userTableIcon.getSize();
    }

    public String getPageHeading() {
        return pageHeading.getText();
    }

}
