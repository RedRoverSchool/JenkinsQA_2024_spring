package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class CreateNewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(css = "[class$='FreeStyleProject']")
    private WebElement freestyleItem;

    @FindBy(id = "from")
    private WebElement nameTextInCopyForm;

    @FindBy(css = "[class$='WorkflowJob']")
    private WebElement pipelineItem;

    @FindBy(css = "[class$='MatrixProject']")
    private WebElement multiConfigurationItem;

    @FindBy(css = "[class$='_Folder']")
    private WebElement folderItem;

    @FindBy(css = "[class*='WorkflowMultiBranchProject']")
    private WebElement multibranchPipelineItem;

    @FindBy(css = "[class$='OrganizationFolder']")
    private WebElement organizationFolderItem;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(id = "itemname-invalid")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@class='item-copy']//li[not(@style='display: none;')]")
    private List<WebElement> copyFormElements;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public HomePage createNewItem(String projectName, String projectType) {
        setItemName(projectName);
        switch (projectType) {
            case "Freestyle" -> freestyleItem.click();
            case "Pipeline" -> pipelineItem.click();
            case "MultiConfiguration" -> multiConfigurationItem.click();
            case "Folder" -> folderItem.click();
            case "MultibranchPipeline" -> multibranchPipelineItem.click();
            case "OrganizationFolder" -> organizationFolderItem.click();
            default -> throw new IllegalArgumentException("Project type name incorrect");
         }
        okButton.click();
        clickLogo();

        return new HomePage(getDriver());
    }


    public CreateNewItemPage setItemName(String name) {
        nameText.sendKeys(name);
        return this;
    }



    public CreateNewItemPage selectTypeAndClickOk(String type) {
        getDriver().findElement(By.xpath("//span[text()='" + type + "']")).click();
        okButton.click();
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

    public String getCreateNewItemPageUrl() {
        return TestUtils.getBaseUrl() + "/view/all/newJob";
    }

    public CreateNewItemPage setItemNameInCopyForm(String name) {
        nameTextInCopyForm.sendKeys(name);
        return this;
    }

    public List<String> copyFormElementsList() {
        return copyFormElements
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public CreateItemPage clickOkButton() {
        okButton.click();
        return new CreateItemPage(getDriver());
    }
}
