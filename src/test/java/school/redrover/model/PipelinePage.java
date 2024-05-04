package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class PipelinePage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement changeDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description>:first-child")
    private WebElement displayedDescription;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public PipelinePage clickChangeDescription() {
        changeDescriptionButton.click();

        return this;
    }

    public PipelinePage setDescription(String name) {
        descriptionInput.sendKeys(name);

        return this;
    }

    public String getDescriptionText() {
        return displayedDescription.getText();
    }

    public PipelinePage waitAddDescriptionButtonDisappears() {
        getWait2().until(ExpectedConditions.invisibilityOf(changeDescriptionButton));

        return this;
    }

    public String getTextAreaBorderBacklightColor() {
        return getDriver().switchTo().activeElement().getCssValue("box-shadow").split(" 0px")[0];
    }

    public PipelinePage clickOnDescriptionInput() {
        descriptionInput.click();

        return this;
    }
}
