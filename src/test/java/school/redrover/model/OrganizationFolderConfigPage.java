package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigPage extends BasePage {

    @FindBy(id = "tasks")
    private WebElement sidebarMenu;

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
}
