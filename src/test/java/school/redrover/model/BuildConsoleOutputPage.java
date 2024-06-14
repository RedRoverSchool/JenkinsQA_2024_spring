package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class BuildConsoleOutputPage extends BasePage<BuildConsoleOutputPage> {

    @FindBy(css = "[class$='output']")
    private WebElement consoleOutput;

    public BuildConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleOutputMessage() {
        return consoleOutput.getText();
    }
}
