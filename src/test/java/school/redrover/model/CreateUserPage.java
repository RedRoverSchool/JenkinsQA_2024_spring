package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateUserPage extends BasePage {

    @FindBy(id = "username")
    private WebElement userNameInput;

    @FindBy(name = "password1")
    private WebElement password1Input;

    @FindBy(name = "password2")
    private WebElement password2Input;

    @FindBy(name = "fullname")
    private WebElement fullNameInput;

    @FindBy(name = "email")
    private WebElement emailAddressInput;

    @FindBy(name = "Submit")
    private WebElement createUserButton;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage setUserName(String userName) {
        userNameInput.sendKeys(userName);

        return new CreateUserPage(getDriver());
    }

    public CreateUserPage setPassword(String password) {
        password1Input.sendKeys(password);

        return new CreateUserPage(getDriver());
    }

    public CreateUserPage setConfirmPassword(String confirmPassword) {
        password2Input.sendKeys(confirmPassword);

        return new CreateUserPage(getDriver());
    }

    public CreateUserPage setFullName(String fullName) {
        fullNameInput.sendKeys(fullName);

        return new CreateUserPage(getDriver());
    }

    public CreateUserPage setEmailAddress(String emailAddress) {
        emailAddressInput.sendKeys(emailAddress);

        return new CreateUserPage(getDriver());
    }

    public UsersPage clickCreateUser() {
        createUserButton.click();

        return new UsersPage(getDriver());
    }

}
