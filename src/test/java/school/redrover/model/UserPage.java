package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class UserPage extends BasePage {
    @FindBy(id = "tasks")
    private WebElement sideMenu;

    @FindBy(linkText = "Configure")
    private WebElement configureSideMenu;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    protected UserConfigurePage clickConfigureSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOf(sideMenu));
        configureSideMenu.click();

        return new UserConfigurePage(getDriver());
    }

}
