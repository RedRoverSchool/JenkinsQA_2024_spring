package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class UserPage extends BasePage {

    @FindBy(css = "#description + div")
    private WebElement jenkinsUserID;

    @FindBy(id = "tasks")
    private WebElement sideMenu;

    @FindBy(linkText = "Configure")
    private WebElement configureSideMenu;

    public UserPage(WebDriver driver) { super(driver); }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getUserID() {

        return jenkinsUserID.getText().replace("Jenkins User ID:", "").trim();
    }

    protected UserConfigurePage clickConfigureSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOf(sideMenu));
        configureSideMenu.click();

        return new UserConfigurePage(getDriver());
    }

}
