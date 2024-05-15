package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleProjectPage> {

    @FindBy(css = "#side-panel h1")
    private WebElement headerSidePanel;

    @FindBy (xpath = "//*[@name='description']")
    private WebElement descriptionField;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver, new FreestyleProjectPage(driver));
    }

    public String getHeaderSidePanelText() {
        return headerSidePanel.getText();
    }

    public FreestyleConfigPage setDescription(String description) {
        descriptionField.sendKeys(description);

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleConfigPage clearDescription() {
        descriptionField.clear();

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleConfigPage clearAndSetDescription(String description) {
        descriptionField.clear();
        descriptionField.sendKeys(description);

        return new FreestyleConfigPage(getDriver());
    }
}
