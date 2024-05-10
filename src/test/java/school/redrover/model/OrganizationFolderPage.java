package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class OrganizationFolderPage extends BasePage {

    @FindBy(css = "span > a[href$='configure']")
    private WebElement configureButton;

    @FindBy(css = "h1 > svg")
    private WebElement itemIcon;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getOrganizationFolderIcon() {
        return itemIcon.getAttribute("title");
    }
}
