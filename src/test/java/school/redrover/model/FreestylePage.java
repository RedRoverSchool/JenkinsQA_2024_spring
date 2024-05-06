package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class FreestylePage extends BasePage {

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    public FreestylePage(WebDriver driver) {
        super(driver);
    }

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }
}