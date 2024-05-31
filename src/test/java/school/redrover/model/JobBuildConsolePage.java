package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class JobBuildConsolePage extends BasePage<JobBuildConsolePage> {

    @FindBy(className = "console-output")
    private WebElement consoleLogsTextBox;

    public JobBuildConsolePage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleLogsText() {

        return getWait5().until(ExpectedConditions.visibilityOf(consoleLogsTextBox)).getText();
    }


}
