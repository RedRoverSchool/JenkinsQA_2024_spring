package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.PeoplePage;
import school.redrover.model.UserConfigurePage;

public abstract class BaseSideMenuPage<T extends BasePage> extends BasePage {

    @FindBy(id = "tasks")
    private WebElement sidebar;

    @FindBy(linkText = "People")
    private WebElement peopleOnSidebar;

    @FindBy(linkText = "New Item")
    private WebElement newItemOnSidebar;

    @FindBy(linkText = "Configure")
    private WebElement configureOnSidebar;

    public BaseSideMenuPage(WebDriver driver) {
        super(driver);
    }

    public PeoplePage clickPeople() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(sidebar));
        peopleOnSidebar.click();

        return new PeoplePage(getDriver());
    }

    public CreateNewItemPage clickNewItemOnSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(newItemOnSidebar)).click();

        return new CreateNewItemPage(getDriver());
    }

    public UserConfigurePage clickConfigureOnSidebar() {
        getWait5().until(ExpectedConditions.visibilityOf(sidebar));
        configureOnSidebar.click();

        return new UserConfigurePage(getDriver());
    }
}
