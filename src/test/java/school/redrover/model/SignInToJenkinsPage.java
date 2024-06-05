package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SignInToJenkinsPage extends BasePage<SignInToJenkinsPage> {

    @FindBy(xpath = "//h1")
    WebElement mainTitle;

    public SignInToJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getSignInToJenkinsTitle() {

     return mainTitle.getText();
    }
}
