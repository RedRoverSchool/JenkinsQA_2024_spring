package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestylePage extends BasePage {

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    @FindBy(xpath = "//*[@id='main-panel']//h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[contains(@href, 'confirm-rename')]")
    private WebElement renameButton;

    @FindBy(id = "description-link")
    private WebElement addDescription;

    @FindBy(xpath = "//textarea")
    private WebElement description;

    @FindBy(xpath = "//button[@formnovalidate]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div")
    private WebElement textDescription;

    public FreestylePage(WebDriver driver) {
        super(driver);
    }

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public FreestyleRenamePage clickRename() {

        renameButton.click();

        return new FreestyleRenamePage(getDriver());
    }
    public FreestylePage clickAddDescription() {
        addDescription.click();
        return this;
    }
    public FreestylePage setDescription(String text) {
        description.sendKeys(text);
        return this;
    }
    public FreestylePage clickSaveButton() {
        saveButton.click();
        return this;
    }
    public String getDescriptionText() {
        return textDescription.getText();
    }
}