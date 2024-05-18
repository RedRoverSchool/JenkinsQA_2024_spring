package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class OrganizationFolderConfigPage extends BasePage {

    @FindBy(xpath = "//*[@name='Submit']")
    private WebElement saveButton;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderPage clickSave() {
        getSaveButton().click();

        return new OrganizationFolderPage(getDriver());
    }

    public WebElement getSaveButton() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(saveButton));
    }

    public void clickSaveButton() {
        getSaveButton().click();
    }


}
