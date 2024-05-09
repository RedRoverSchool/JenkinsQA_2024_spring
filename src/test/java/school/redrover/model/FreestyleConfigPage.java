package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleConfigPage extends BasePage {

    @FindBy (xpath = "//*[@name='description']")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigPage inputDescription(String description) {
        descriptionField.sendKeys(description);

        return new FreestyleConfigPage(getDriver());
    }

    public FreestylePage clickSave() {
        saveButton.click();

        return new FreestylePage(getDriver());
    }
}
