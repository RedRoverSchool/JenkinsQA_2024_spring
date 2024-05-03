package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class FreestyleConfigPage extends BasePage {
    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return new FreestyleConfigPage(getDriver());
    }
}
