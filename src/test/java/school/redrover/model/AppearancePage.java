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

    @FindBy(css = "[for='radio-block-0']")
    private WebElement darkThemeButton;

    @FindBy(css = "[for='radio-block-1']")
    private WebElement systemThemeButton;

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

    public ManageJenkinsPage clickApply(){
        applyButton.click();
        return new ManageJenkinsPage(getDriver());
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
}
