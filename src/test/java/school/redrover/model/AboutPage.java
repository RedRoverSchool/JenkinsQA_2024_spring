package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class AboutPage extends BasePage{

    @FindBy(xpath = "//*[@id='main-panel']/div[2]/div[1]/p")
    private WebElement jenkinsVersion;

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String jenkinsVersion() {
        return jenkinsVersion.getText();
    }
}
