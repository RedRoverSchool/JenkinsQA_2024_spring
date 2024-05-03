package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class CreateNewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(xpath = "//label[.='Organization Folder']")
    private WebElement organizationFolderItem;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(css = "[class$='_Folder']")
    private WebElement folderItem;

    @FindBy(css = "[class*='WorkflowMultiBranchProject']")
    private WebElement multibranchPipelineItem;

    @FindBy(id = "itemname-invalid")
    private WebElement errorMessage;

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

    public FolderConfigPage selectFolderAndClickOk() {
        folderItem.click();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        multibranchPipelineItem.click();
        okButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public CreateNewItemPage selectFolder() {
        folderItem.click();

        return this;
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
