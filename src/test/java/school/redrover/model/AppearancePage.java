package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AppearancePage extends BasePage {
    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getThemesList() {

        return getDriver().findElements(By.className("app-theme-picker__item"));
    }
}
