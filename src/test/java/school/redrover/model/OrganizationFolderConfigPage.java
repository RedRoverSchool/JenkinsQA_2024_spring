package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigPage extends BasePage {

    @FindBy(id = "tasks")
    private WebElement sidebarMenu;

    @FindBy(xpath = "(//select[contains(@class, 'dropdownList')])[2]")
    private WebElement iconDropdownList;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return new OrganizationFolderPage(getDriver());
    }

    public boolean isSidebarVisible() {
        return sidebarMenu.isDisplayed();
    }

    public OrganizationFolderConfigPage clickIconDropdownList() {
        iconDropdownList.click();

        return this;
    }

    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(iconDropdownList)
                .selectByVisibleText("Default Icon");

        return new OrganizationFolderConfigPage(getDriver()RF)
    }
}
