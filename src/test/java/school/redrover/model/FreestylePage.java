package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class FreestylePage extends BasePage {

    @FindBy(xpath = "//a[@tooltip='Success > Console Output']")
    private WebElement successConsoleOutputButton;

    public FreestylePage(WebDriver driver) {
        super(driver);
    }

    public JobBuildConsolePage clickSuccessConsoleOutputButton() {
        getWait60().until(ExpectedConditions.elementToBeClickable(successConsoleOutputButton)).click();

        return new JobBuildConsolePage(getDriver());
    }
}