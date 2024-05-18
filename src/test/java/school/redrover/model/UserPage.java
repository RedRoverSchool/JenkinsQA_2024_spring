package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseSideMenuPage;

public class UserPage extends BaseSideMenuPage<UserPage> {

    @FindBy(css = "#description + div")
    private WebElement jenkinsUserID;

    public UserPage(WebDriver driver) { super(driver); }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getUserID() {

        return jenkinsUserID.getText().replace("Jenkins User ID:", "").trim();
    }

}
