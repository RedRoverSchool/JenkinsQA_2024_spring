package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class UserPage extends BasePage<UserPage> {

    @FindBy(css = "#description + div")
    private WebElement jenkinsUserID;

    @FindBy(linkText = "Configure")
    private WebElement configureOnSidebar;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    public String getUserID() {

        return jenkinsUserID.getText();
    }

    @Step("Click 'Configure' on sidebar")
    public UserConfigurePage clickConfigureOnSidebar() {
        getWait5().until(ExpectedConditions.visibilityOf(configureOnSidebar)).click();

        return new UserConfigurePage(getDriver());
    }


}
