package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultiConfigurationConfigPage extends BasePage {

    @FindBy(className = "jenkins-toggle-switch__label")
    private WebElement toggleSwitch;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return new MultiConfigurationPage(getDriver());
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
}
