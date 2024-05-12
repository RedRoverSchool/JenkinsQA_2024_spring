package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;

public class MultiConfigurationConfigPage extends BaseConfigPage<MultiConfigurationProjectPage> {

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement toggleSwitch;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    @FindBy(id = "itemname-required")
    private WebElement errorRequiresName;

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver, new MultiConfigurationProjectPage(driver));
    }

    public MultiConfigurationConfigPage clickToggleSwitch() {
        toggleSwitch.click();

        return this;
    }

    public MultiConfigurationConfigPage clickApply() {
        applyButton.click();

        return this;
    }

    public boolean getStatusToggle() {
        return toggleSwitch.isSelected();
    }
    public String getErrorRequiresName() {
        return errorRequiresName.getText();
    }
}
