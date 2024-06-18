package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class MultibranchPipelineProjectPage extends BaseProjectPage<MultibranchPipelineProjectPage> {

    @FindBy(xpath = "//span[.='Configure the project']")
    private WebElement configureButton;

    @FindBy(name = "Submit")
    private WebElement enableMPButton;

    @FindBy(xpath = "//form[@id='disable-project']/button")
    private WebElement disableProjectButton;

    @FindBy(id = "enable-project")
    private WebElement disableMPMessage;

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Configure' on sidebar")
    public MultibranchPipelineConfigPage clickConfigure() {
        configureButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    @Step("Click 'Enable' button")
    public MultibranchPipelineProjectPage clickEnableButton() {
        enableMPButton.click();

        return this;
    }

    @Step("Click 'Disable Multibranch Pipeline'")
    public MultibranchPipelineProjectPage clickDisableProjectButton() {
        disableProjectButton.click();

        return this;
    }

    public String getDisableMultibranchPipelineButtonText() {
        return enableMPButton.getText().trim();
    }

    public String getTextOfDisableMultibranchPipeline() {
        return disableMPMessage.getDomProperty("innerText").split(" Enable")[0];
    }

    public String getColorOfDisableMultibranchPipelineText() {
        return disableMPMessage.getCssValue("color");
    }

}
