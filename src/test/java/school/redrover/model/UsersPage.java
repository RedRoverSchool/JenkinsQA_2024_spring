package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersPage extends BasePage<UsersPage> {

    @FindBy(css = "[href='addUser']")
    private WebElement createUserLink;

    @FindBy(css = "[class*='jenkins-table__link']")
    List<WebElement> usersList;

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

    public CreateUserPage clickCreateUser() {
        createUserLink.click();

        return new CreateUserPage(getDriver());
    }

    public List<String> getUsersList() {
        return usersList.stream()
                .map(WebElement::getText)
                .toList();
    }

    public UsersPage createUserWithRandomData() {
        String password = randomString();
        clickCreateUser()
                .typeUserName(randomString())
                .setPassword(password)
                .setConfirmPassword(password)
                .setFullName(randomString())
                .setEmailAddress(randomEmail())
                .clickCreateUser();

        return this;
    }

    public String randomString() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 7);
    }

    public String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public UsersPage clickColumnNameHeader() {
        columnNameHeader.click();

        return this;
    }

    public UsersPage clickColumnUserIDHeader() {
        columnUserIDHeader.click();

        return this;
    }

    public List<String> getUserNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : userNameList) {
            names.add(element.getText());
        }

        return names;
    }

    public List<String> getUserIDList() {

        return userIDList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public UsersPage createUser(String username, String password, String fullName, String email) {

        clickCreateUser()
                .typeUserName(username)
                .setPassword(password)
                .setConfirmPassword(password)
                .setFullName(fullName)
                .setEmailAddress(email)
                .clickCreateUser();

        return this;
    }
}
