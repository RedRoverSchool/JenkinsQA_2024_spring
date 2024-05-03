package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class MultiConfigurationConfigPage extends BasePage {

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return new MultiConfigurationPage(getDriver());
    }
}
