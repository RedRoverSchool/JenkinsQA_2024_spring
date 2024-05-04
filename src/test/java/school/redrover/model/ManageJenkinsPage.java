package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    @FindBy(css = "[href='configureSecurity']")
    private WebElement securityLink;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public SecurityPage clickSecurity() {
        securityLink.click();

        return new SecurityPage(getDriver());
    }
}
