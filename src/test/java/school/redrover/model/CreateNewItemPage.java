package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class CreateNewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(xpath = "//label[.='Organization Folder']")
    private WebElement organizationFolderItem;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement itemNameField;

    @FindBy(xpath = "//*[text()='Folder']/ancestor::li")
    private WebElement folderRadioButton;

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

    public WebElement getItemNameField() {

        return getWait5().until(ExpectedConditions.visibilityOf(itemNameField));
    }

    public WebElement getFolderRadioButton() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(folderRadioButton));
    }

    public WebElement getOkButton() {

        return getWait5().until(ExpectedConditions.elementToBeClickable(okButton));
    }

    public void inputItemName(String folderName) {
        getItemNameField().sendKeys(folderName);
    }

    public void clickFolderRadioButton () {
        getFolderRadioButton().click();
    }

    public void clickOkButton() {
        getOkButton().click();
    }

    public CreateNewItemPage inputItemNameChain(String folderName) {
        getItemNameField().sendKeys(folderName);

        return this;
    }

    public CreateNewItemPage clickFolderRadioButtonChain() {
        getFolderRadioButton().click();

        return this;
    }
}
