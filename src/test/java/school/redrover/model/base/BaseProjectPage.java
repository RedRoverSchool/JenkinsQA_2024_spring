package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.PipelineProjectPage;

public abstract class BaseProjectPage<T extends BaseProjectPage<T>> extends BasePage<T> {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionInput;


    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    @Step("Click on 'Add description'")
    public T clickAddDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Click on 'Edit description'")
    public T clickEditDescription() {
        addOrEditDescriptionButton.click();

        return (T) this;
    }

    @Step("Check 'Add description button")
    public boolean isAddDescriptionButtonEnable() {
        return addOrEditDescriptionButton.isEnabled();
    }

    public T typeDescription(String text) {
        descriptionInput.sendKeys(text);

        return (T) this;
    }

    public T waitAddDescriptionButtonDisappears() {
        getWait2().until(ExpectedConditions.invisibilityOf(addOrEditDescriptionButton));

        return (T) this;
    }

    public T clickOnDescriptionInput() {
        descriptionInput.click();

        return (T) this;
    }

    public String getDefaultTextAreaBorderBacklightColor() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        return (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                descriptionInput);
    }

    public T clickSaveButton() {
        saveButton.click();

        return (T) this;
    }

}
