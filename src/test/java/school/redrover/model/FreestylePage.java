package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class FreestylePage extends BasePage {

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    @FindBy(xpath = "//*[@id='main-panel']//h1")
    private WebElement projectName;

    public FreestylePage(WebDriver driver) {
        super(driver);
    }

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }

    public String getProjectName() {

        return projectName.getText();
    }
}