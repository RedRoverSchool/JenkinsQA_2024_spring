package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseConfigPage;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderProjectPage> {

    @FindBy(id = "tasks")
    private WebElement sidebarMenu;

    @FindBy(xpath = "(//select[contains(@class, 'dropdownList')])[2]")
    private WebElement iconDropdownList;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver, new OrganizationFolderProjectPage(driver));
    }

    public boolean isSidebarVisible() {
        return sidebarMenu.isDisplayed();
    }

    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(iconDropdownList)
                .selectByVisibleText("Default Icon");

        return new OrganizationFolderConfigPage(getDriver());
    }
}
