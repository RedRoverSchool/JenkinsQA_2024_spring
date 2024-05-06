package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AppearancePage extends BasePage {

    @FindBy(className = "app-theme-picker__item")
    private List<WebElement> themesList;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getThemesList() { return themesList; }
}
