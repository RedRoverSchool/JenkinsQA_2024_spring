package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import org.openqa.selenium.Dimension;

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

    @FindBy(id = "people")
    private WebElement peopleTable;

    @FindBy(xpath = "//a[contains(@href, '/user/')]")
    private WebElement userIdLink;

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

    protected UserPage clickUserIdLink() {
        getWait5().until(ExpectedConditions.visibilityOf(peopleTable));
        userIdLink.click();

        return new UserPage(getDriver());
    }

}
