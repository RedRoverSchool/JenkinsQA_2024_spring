package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class CreateNewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(xpath = "//label[.='Freestyle project']")
    private WebElement freestyleItem;

    @FindBy(xpath = "//label[.='Pipeline']")
    private WebElement pipelineItem;

    @FindBy(xpath = "//label[.='Multi-configuration project']")
    private WebElement multiConfigurationItem;

    @FindBy(css = "[class$='_Folder']")
    private WebElement folderItem;

    @FindBy(css = "[class*='WorkflowMultiBranchProject']")
    private WebElement multibranchPipelineItem;

    @FindBy(xpath = "//label[.='Organization Folder']")
    private WebElement organizationFolderItem;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(id = "itemname-invalid")
    private WebElement errorMessage;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage setItemName(String name) {
        getWait5().until(ExpectedConditions.visibilityOf(nameText));
        nameText.sendKeys(name);

        return this;
    }

    public FreestyleConfigPage selectFreestyleAndClickOk() {
        freestyleItem.click();
        okButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    public PipelineConfigPage selectPipelineAndClickOk() {
        pipelineItem.click();
        okButton.click();

        return new PipelineConfigPage(getDriver());
    }

    public MultiConfigurationConfigPage selectMultiConfigurationAndClickOk() {
        multiConfigurationItem.click();
        okButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }

    public CreateNewItemPage selectMultiConfiguration() {
        multiConfigurationItem.click();

        return this;
    }

    public FolderConfigPage selectFolderAndClickOk() {
        folderItem.click();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public CreateNewItemPage selectFolder() {
        folderItem.click();

        return this;
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        multibranchPipelineItem.click();
        okButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage selectOrganizationFolderAndClickOk() {
        organizationFolderItem.click();
        okButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public <T> T clickOkAnyway(T page) {
        okButton.click();

        return page;
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

}
