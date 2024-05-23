package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class CreateNewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameItemField;

    @FindBy(xpath = "//label/span[text()='Folder']/ancestor::li")
    private WebElement folderButton;

    @FindBy(xpath = "//label[.='Organization Folder']")
    private WebElement organizationFolderItem;

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage setItemName(String name) {
        nameText.sendKeys(name);
        return this;
    }

    public OrganizationFolderConfigPage selectOrganizationFolderAndClickOk() {
        organizationFolderItem.click();
        okButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }
    public WebElement getNameItemField(){

        return getWait5().until(ExpectedConditions.visibilityOf(nameItemField));
    }

    public WebElement getFolderButton() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(folderButton));
    }

    public WebElement getOkButton() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(okButton));
    }
}
