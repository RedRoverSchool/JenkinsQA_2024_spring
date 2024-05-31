package school.redrover.model;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class AdminConfigurePage extends BasePage<AdminConfigurePage> {

    @FindBy(css = "[class*='-main'] [class*='box']")
    private WebElement searchSettingsBlock;

    @FindBy(css = "[name^='insensitive']")
    private WebElement caseSensitivityCheckbox;

    @FindBy(css = "button[name='Apply']")
    private WebElement applyButton;

    public AdminConfigurePage(WebDriver driver) {
        super(driver);
    }

    public AdminConfigurePage clickApplyButton() {
        applyButton.click();
        return new AdminConfigurePage(getDriver());
    }

    public AdminConfigurePage turnInsensitiveSearch(boolean flag) {
        boolean isCheckboxSelected = caseSensitivityCheckbox.isSelected();
        if ((flag && !isCheckboxSelected) || (!flag && isCheckboxSelected)) {
            new Actions(getDriver()).moveToElement(searchSettingsBlock)
                    .sendKeys(Keys.TAB, Keys.SPACE)
                    .perform();
        }
        return new AdminConfigurePage(getDriver());
    }
}
