package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class PipelineConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionTextArea;

    public PipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return new PipelinePage(getDriver());
    }

    public PipelineConfigPage addDescription(String descriptionText) {
        getWait2().until(ExpectedConditions.visibilityOf(descriptionTextArea)).sendKeys(descriptionText);

        return this;
    }
}
