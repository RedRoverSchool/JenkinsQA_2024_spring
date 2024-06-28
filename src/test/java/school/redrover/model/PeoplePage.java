package school.redrover.model;

import io.qameta.allure.Step;
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
    private WebElement iconS;

    @FindBy(css = "[tooltip='Medium']")
    private WebElement iconM;

    @FindBy(css = "[title='Large']")
    private WebElement iconL;

    @FindBy(css = "[id*='person-admin'] svg")
    private WebElement userTableIcon;

    @FindBy(id = "people")
    private WebElement peopleTable;

    @FindBy(xpath = "//a[contains(@href, '/user/')]")
    private WebElement userIdLink;

    @FindBy(css = ".jenkins-table__link")
    private List<WebElement> userIDList;

    @FindBy(linkText = "User ID")
    private WebElement titleUserID;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(3)")
    private List<WebElement> nameList;

    @FindBy(linkText = "Name")
    private WebElement titleName;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(4)")
    private List<WebElement> lastCommitActivityList;

    @FindBy(linkText = "Last Commit Activity")
    private WebElement titleLastCommitActivity;

    @FindBy(css = "tr[id^='person-'] > td:nth-child(5)")
    private List<WebElement> onOffList;

    @FindBy(linkText = "On")
    private WebElement titleOn;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click '{userID}' user")
    public UserPage clickUser(String userID) {
        getDriver().findElement(By.cssSelector("td > [href*='" + userID.toLowerCase() + "']")).click();

        return new UserPage(getDriver());
    }

    @Step("Click on the icon 'S' to change icon size to Small")
    public PeoplePage clickIconS() {
        iconS.click();

        return this;
    }

    @Step("Click on the icon 'M' to change icon size to Medium")
    public PeoplePage clickIconM() {
        iconM.click();

        return this;
    }

    @Step("Click on the icon 'L' to change icon size to Large")
    public PeoplePage clickIconL() {
        iconL.click();

        return this;
    }

    @Step("Get user icon size")
    public Dimension getUserIconSize() {
        return userTableIcon.getSize();
    }

    @Step("Click first 'User Id' link")
    public UserPage clickUserIdLink() {
        getWait5().until(ExpectedConditions.visibilityOf(peopleTable));
        userIdLink.click();

        return new UserPage(getDriver());
    }

    @Step("Get list from column 'User ID'")
    public List<String> getUserIDList() {
        return userIDList.stream().map(WebElement::getText).toList();
    }

    @Step("Click on the title 'User ID'")
    public PeoplePage clickTitleUserID() {
        titleUserID.click();

        return this;
    }

    @Step("Get user name list")
    public List<String> getNamesList() {
        return nameList.stream().map(WebElement::getText).toList();
    }

    @Step("Click on the title 'Name'")
    public PeoplePage clickTitleName() {
        titleName.click();

        return this;
    }

    @Step("Get list from column 'Last Commit Activity'")
    public List<String> getLastCommitActivityList() {
        return lastCommitActivityList.stream().map(WebElement::getText).toList();
    }

    @Step("Click on the title 'Last Commit Activity'")
    public PeoplePage clickTitleLastCommitActivity() {
        titleLastCommitActivity.click();

        return this;
    }

    @Step("Get list from column 'On'")
    public List<String> getOnOffList() {
        return onOffList.stream().map(WebElement::getText).toList();
    }

    @Step("Click on the title 'On'")
    public PeoplePage clickTitleOn() {
        titleOn.click();

        return this;
    }
}
