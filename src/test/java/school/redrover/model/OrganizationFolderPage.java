package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderPage extends BasePage {

    @FindBy(id = "tasks")
    private WebElement sidebarMenu;

    @FindBy(css = "span > a[href$='configure']")
    private WebElement configureButton;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSidebarVisible() {
        return sidebarMenu.isDisplayed();
    }

    public void clickConfigure() {
        configureButton.click();
    }
}
