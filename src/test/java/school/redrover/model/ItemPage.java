package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ItemPage extends BasePage {

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement ElementPeople;

    @FindBy(xpath = "//h1[contains(.,'Welcome to Jenkins!')]")
    private WebElement ElementWelcome;

    @FindBy(linkText = "New Item")
    private WebElement NewItem;

    @FindBy(xpath = "//img[@class='icon-freestyle-project icon-xlg']")
    private WebElement FreestyleProject;

    @FindBy(xpath = "//span[contains(text(),  'Pipeline')]")
    private WebElement Pipeline;
    @FindBy(xpath = "//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")
    private WebElement btnOK;

    @FindBy(id = "name")
    private WebElement NewItemName;

    @FindBy(xpath = "//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")
    private WebElement Folder;

    @FindBy(name = "Submit")
    private WebElement SaveBtn;

    public ItemPage setItemName(String name) {
        NewItemName.sendKeys(name);

        return this;
    }

    public ItemPage NewItemName(String name) {
        NewItemName.sendKeys(name);

        return this;
    }

    public ItemPage clickButtonOK() {
        btnOK.click();

        return this;
    }

    public ItemPage clickSaveBtn() {
        SaveBtn.click();

        return this;
    }

    public ItemPage NewItemClick() {
        NewItem.click();

        return this;
    }

    public ItemPage FreestyleProjectClick() {
        FreestyleProject.click();

        return this;
    }

    public ItemPage ElementPeopleClick() {
        ElementPeople.click();

        return this;
    }

    public ItemPage ElementWelcomeClic() {
        ElementWelcome.click();

        return this;
    }

    public ItemPage selectFolderType() {
        Folder.click();

        return this;
    }

    public ItemPage PipelineClic() {
        Pipeline.click();

        return this;
    }
}