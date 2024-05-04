package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SecurityPage extends BasePage {


    public SecurityPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return getDriver().getTitle().split(" ")[0];
    }
}
