package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage {

    @FindBy(css = "[href='addUser']")
    private WebElement createUserLink;

    @FindBy(css = "[class*='jenkins-table__link']")
    List<WebElement> usersList;

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
}
