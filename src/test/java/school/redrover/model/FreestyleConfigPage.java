package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleProjectPage> {

    @FindBy (xpath = "//*[@name='description']")
    private WebElement descriptionField;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver, new FreestyleProjectPage(driver));
    }

    public FreestyleConfigPage inputDescription(String description) {
        descriptionField.sendKeys(description);

        return new FreestyleConfigPage(getDriver());
    }
}
