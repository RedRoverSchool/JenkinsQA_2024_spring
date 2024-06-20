package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AppearancePage extends BasePage<AppearancePage> {

    @FindBy(className = "app-theme-picker__item")
    private List<WebElement> themesList;

    @FindBy(css = "[for='radio-block-0']")
    private WebElement darkThemeButton;

    @FindBy(css = "[for='radio-block-1']")
    private WebElement systemThemeButton;

    @FindBy(css = "[for='radio-block-2']")
    private WebElement defaultThemeButton;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    @FindBy(id = "notification-bar")
    private WebElement notification;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getThemesList() {
        return themesList;
    }

    public boolean isDefaultThemeNotSelected() {
        return !defaultThemeButton.isSelected();
    }

    public AppearancePage clickDarkThemeButton() {
        darkThemeButton.click();

        return this;
    }

    public AppearancePage clickSystemThemeButton() {
        systemThemeButton.click();

        return this;
    }

    public AppearancePage clickDefaultThemeButton() {
        defaultThemeButton.click();

        return this;
    }

    public AppearancePage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public AppearancePage clickApply() {
        applyButton.click();

        return new AppearancePage(getDriver());
    }

    public AppearancePage switchToDefaultTheme() {
        if (isDefaultThemeNotSelected()) {
            clickDefaultThemeButton();
            clickApplyButton();
        }

        return this;
    }

    public String getCurrentThemeAttribute() {
        return getDriver().findElement(By.cssSelector("html[data-theme]"))
                .getAttribute("data-theme");
    }

    public String getNotificationText() {
        return getWait2().until(ExpectedConditions.visibilityOf(notification)).getText();
    }

}
