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

    @FindBy(css = "div#j-add-item-type-nested-projects > ul > li > div:nth-of-type(2) > img")
    private WebElement imgItem;

    public ItemPage setItemName(String name) {
        NewItemName.sendKeys(name);

        return this;
    }

    public ItemPage newItemName(String name) {
        NewItemName.sendKeys(name);

        return this;
    }

    public ItemPage imgItem() {
        imgItem.click();

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

    public ItemPage newItemClick() {
        NewItem.click();

        return this;
    }

    public ItemPage freestyleProjectClick() {
        FreestyleProject.click();

        return this;
    }

    public ItemPage elementPeopleClick() {
        ElementPeople.click();

        return this;
    }

    public ItemPage elementWelcomeClic() {
        ElementWelcome.click();

        return this;
    }

    public ItemPage selectFolderType() {
        Folder.click();

        return this;
    }

    public ItemPage pipelineClic() {
        Pipeline.click();

        return this;
    }
}