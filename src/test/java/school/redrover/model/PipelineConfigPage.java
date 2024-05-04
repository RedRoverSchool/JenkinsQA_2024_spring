package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class PipelineConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;
    public PipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return new PipelinePage(getDriver());
    }
}
