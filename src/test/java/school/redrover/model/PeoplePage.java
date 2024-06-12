package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class PeoplePage extends BasePage<PeoplePage> {

    @FindBy(css = "[title='Small']")
    private WebElement smallIconButton;

    @FindBy(css = "[tooltip='Medium']")
    private WebElement mediumIconButton;

    @FindBy(css = "[title='Large']")
    private WebElement largeIconButton;

    @FindBy(css = "[id*='person-admin'] svg")
    private WebElement userTableIcon;

    @FindBy(id = "people")
    private WebElement peopleTable;

    @FindBy(xpath = "//a[contains(@href, '/user/')]")
    private WebElement userIdLink;

    @FindBy(css = ".jenkins-table__link")
    private List<WebElement> userIDList;

    @FindBy(linkText= "User ID")
    WebElement titleUserID;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(3)")
    private List<WebElement> nameList;

    @FindBy(linkText= "Name")
    WebElement titleName;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(4)")
    private List<WebElement> lastCommitActivityList;

    @FindBy(linkText= "Last Commit Activity")
    WebElement titleLastCommitActivity;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(5)")
    private List<WebElement> OnOffList;

    @FindBy(linkText= "On")
    WebElement titleOn;

    public PeoplePage(WebDriver driver) {
        super(driver);
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

    public UserPage clickUserIdLink() {
        getWait5().until(ExpectedConditions.visibilityOf(peopleTable));
        userIdLink.click();

        return new UserPage(getDriver());
    }

    public List<String> getUserIDList() {
        return userIDList.stream().map(WebElement::getText).toList();
    }

    public PeoplePage clickTitleUserID() {
        titleUserID.click();

        return this;
    }

    public List<String> getNamesList() {
        return nameList.stream().map(WebElement::getText).toList();
    }

    public PeoplePage clickTitleName() {
        titleName.click();

        return this;
    }

    public List<String> getLastCommitActivityList() {
        return lastCommitActivityList.stream().map(WebElement::getText).toList();
    }

    public PeoplePage clickTitleLastCommitActivity() {
        titleLastCommitActivity.click();

        return this;
    }

    public List<String> getOnOffList() {
        return OnOffList.stream().map(WebElement::getText).toList();
    }

    public PeoplePage clickTitleOn() {
        titleOn.click();

        return this;
    }

}
