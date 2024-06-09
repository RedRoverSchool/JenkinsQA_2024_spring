package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class CreateNewItemPage extends BasePage<CreateNewItemPage> {

    @FindBy(id = "name")
    private WebElement nameText;

    @FindBy(css = "[class$='FreeStyleProject']")
    private WebElement freestyleItem;

    @FindBy(css = "#from")
    private WebElement copyFromInputField;

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
    private WebElement errorItemNameInvalid;

    @FindBy(id = "itemname-required")
    private WebElement errorMessageEmptyName;

    @FindBy(xpath = "//div[@class='item-copy']//li[not(@style='display: none;')]")
    private List<WebElement> copyFormElements;

    @FindBy(id = "itemname-required")
    private WebElement itemNameHint;

    @FindBy(css = "label.h3")
    private WebElement titleOfNameField;

    @FindBy(css = "#items span")
    private List<WebElement> typesList;

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

    public CreateNewItemPage clickProjectType(TestUtils.ProjectType projectType) {
        getDriver().findElement(By.xpath("//span[text()='" + projectType.getProjectTypeName() + "']")).click();

        return this;
    }

    public CreateNewItemPage clickProjectType(String type) {
        getDriver().findElement(By.xpath("//span[text()='" + type + "']")).click();

        return this;
    }

    public String getErrorMessageInvalidCharacterOrDuplicateName() {
        return errorItemNameInvalid.getText();
    }

    public String getErrorMessageEmptyName() {
        return errorMessageEmptyName.getText();
    }


    public CreateNewItemPage typeItemNameInCopyFrom(String name) {
        clickElementFromTheBottomOfThePage(copyFromInputField);
        copyFromInputField.sendKeys(name);

        return this;
    }
    public List<String> getCopyFormElementsList() {
        return copyFormElements
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public CreateItemPage clickOkButton() {
        okButton.click();
        return new CreateItemPage(getDriver());
    }

    public boolean isOkButtonNotActive() {
        try {
            getDriver().findElement(By.xpath("//button[contains(@class, 'disabled') and text()='OK']"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getDropdownMenuContent() {
        List<WebElement> allJobFromThisLetter = getWait10().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li[style='']")));

        return allJobFromThisLetter.stream().map(WebElement::getText).toList();
    }

    public CreateNewItemPage selectFreeStyleProject() {
        freestyleItem.click();
        return this;
    }

    public CreateNewItemPage clearItemNameField() {
        nameText.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        return this;
    }

    public String getItemNameHintText() {
        return itemNameHint.getText();
    }

    public String getItemNameHintColor() {
        return itemNameHint.getCssValue("color");
    }

    public Boolean isOkButtonEnabled() {
        return okButton.isEnabled();
    }

    public String getTitleOfNameField() {
        return titleOfNameField.getText();
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public Boolean isDisplayedNameField() {
        return nameText.isDisplayed();
    }

    public List<String> getTypesList() {
        return typesList.stream().map(WebElement::getText).toList();
    }

    public Boolean isAttributeAriaChecked(String projectType, int itemOptionIndex) {

        return Boolean.parseBoolean(getDriver().findElement(
                        By.xpath(String.format("//div[contains(@id, '%s')]/ul/li[%d]", projectType, itemOptionIndex)))
                .getAttribute("aria-checked"));
    }

    public CreateNewItemPage clickItemOption(String projectType, int itemOptionIndex) {
        getDriver().findElement(
                        By.xpath(String.format("//div[contains(@id, '%s')]/ul/li[%d]", projectType, itemOptionIndex)))
                .click();

        return this;
    }
}
