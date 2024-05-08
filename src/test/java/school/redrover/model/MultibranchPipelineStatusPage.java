package school.redrover.model;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import school.redrover.model.base.BasePage;

public class MultibranchPipelineStatusPage extends BasePage {


    @FindBy(xpath = "//span[.='Configure the project']")
    private WebElement configureButton;

    @FindBy(name = "Submit")
    private WebElement disableEnableMPButton;


    @FindBy(id = "enable-project")
    private WebElement disableMPMessage;

    @FindBy(xpath = "//form[contains(., 'This Multibranch Pipeline is currently disabled')]")
    private List<WebElement> disabledMultiPipelineMessage;

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage selectConfigure() {
        configureButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineStatusPage clickDisableEnableMultibranchPipeline() {
        disableEnableMPButton.click();

        return this;
    }
    public String getDisableMultibranchPipelineButtonText(){
       return disableEnableMPButton.getText().trim();
    }



    public String getDisableMultibranchPipelineText() {
        return disableMPMessage.getDomProperty("innerText").split(" Enable")[0];
    }

    public boolean isMultibranchPipelineDisabledTextNotDisplayed() {
        return disabledMultiPipelineMessage.isEmpty();
    }
}