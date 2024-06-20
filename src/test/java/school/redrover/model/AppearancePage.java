package school.redrover.model;

import io.qameta.allure.Step;
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

    @Step("Get list of Jenkins color themes")
    public List<WebElement> getThemesList() {
        return themesList;
    }

    public boolean isDefaultThemeNotSelected() {
        return !defaultThemeButton.isSelected();
    }

    @Step("Click on the Dark color theme")
    public AppearancePage clickDarkThemeButton() {
        darkThemeButton.click();

        return this;
    }

    @Step("Click on the System color theme")
    public AppearancePage clickSystemThemeButton() {
        systemThemeButton.click();

        return this;
    }

    @Step("Click on the Default color theme")
    public AppearancePage clickDefaultThemeButton() {
        defaultThemeButton.click();

        return this;
    }

    @Step("Click on the 'Apply' button")
    public AppearancePage clickApplyButton() {
        applyButton.click();

        return this;
    }

    @Step("Switch to default color theme")
    public AppearancePage switchToDefaultTheme() {
        if (isDefaultThemeNotSelected()) {
            clickDefaultThemeButton();
            clickApplyButton();
        }

        return this;
    }

    @Step("Get current theme attribute 'data-theme'")
    public String getCurrentThemeAttribute() {
        return getDriver().findElement(By.cssSelector("html[data-theme]"))
                .getAttribute("data-theme");
    }

    @Step("Get notification about apply theme changes")
    public String getNotificationText() {
        return getWait10().until(ExpectedConditions.visibilityOf(notification)).getText();
    }
}
