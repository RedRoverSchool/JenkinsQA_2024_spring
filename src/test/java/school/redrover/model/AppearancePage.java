package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AppearancePage extends BasePage {

    @FindBy(className = "app-theme-picker__item")
    private List<WebElement> themesList;

    @FindBy(css = "[for='radio-block-2']")
    private WebElement defaultThemeButton;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getThemesList() { return themesList; }

    public boolean isDefaultThemeNotSelected() {
        return !defaultThemeButton.isSelected();
    }

    public AppearancePage clickDefaultThemeButton() {
        defaultThemeButton.click();

        return this;
    }

    public AppearancePage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public AppearancePage switchToDefaultTheme() {
        if (isDefaultThemeNotSelected()) {
            clickDefaultThemeButton();
            clickApplyButton();
        }
            return this;
    }
}
