package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.PeoplePage;
import school.redrover.model.UserConfigurePage;

import java.util.List;

public abstract class BaseSideMenuPage<T extends BasePage> extends BasePage {

    @FindBy(id = "tasks")
    private List<WebElement> sideMenus;

    @FindBy(id = "tasks")
    private WebElement sideMenu;

    @FindBy(linkText = "People")
    private WebElement peopleSideMenu;

    @FindBy(linkText = "New Item")
    private WebElement newItemSideMenu;

    @FindBy(linkText = "Configure")
    private WebElement configureSideMenu;

    public BaseSideMenuPage(WebDriver driver) {
        super(driver);
    }

    public PeoplePage clickPeopleSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(sideMenus));
        peopleSideMenu.click();

        return new PeoplePage(getDriver());
    }

    public CreateNewItemPage clickNewItemSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(newItemSideMenu)).click();

        return new CreateNewItemPage(getDriver());
    }

    public UserConfigurePage clickConfigureSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOf(sideMenu));
        configureSideMenu.click();

        return new UserConfigurePage(getDriver());
    }
}
