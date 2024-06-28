package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage<UsersPage> {

    @FindBy(css = "[href='addUser']")
    private WebElement createUserLink;

    @FindBy(css = "thead th:nth-child(3)>a")
    private WebElement columnNameHeader;

    @FindBy(css = "thead th:nth-child(2)>a")
    private WebElement columnUserIDHeader;

    @FindBy(css = "tr>td:nth-child(3)")
    private List<WebElement> userNameList;

    @FindBy(css = "tr>td:nth-child(2)")
    private List<WebElement> userIDList;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Create User' link")
    public CreateUserPage clickCreateUser() {
        createUserLink.click();

        return new CreateUserPage(getDriver());
    }

    @Step("Click 'Name' column header")
    public UsersPage clickColumnNameHeader() {
        columnNameHeader.click();

        return this;
    }

    @Step("Click 'User ID' column header")
    public UsersPage clickColumnUserIDHeader() {
        columnUserIDHeader.click();

        return this;
    }

    @Step("Click '{username}' user")
    public UserPage clickUser(String username) {
        WebElement name = getDriver().findElement(By.cssSelector("td > [href*='" + username + "']"));
        new Actions(getDriver())
                .moveToElement(name)
                .click()
                .perform();

        return new UserPage(getDriver());
    }

    public List<String> getUserNamesList() {
        return userNameList.stream().map(WebElement::getText).toList();
    }

    public List<String> getUserIDList() {
        return userIDList.stream().map(WebElement::getText).toList();
    }
}
