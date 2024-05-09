package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SystemConfigurationPage extends BasePage {

    public SystemConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    public String getTitleText(){
        return pageHeading.getText();
    }
}
