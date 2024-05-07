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
    WebElement ElementPeople;

    @FindBy(xpath = "//h1[contains(.,'Welcome to Jenkins!')]")
    WebElement ElementWelcome;

    @FindBy(linkText = "New Item")
    WebElement NewItem;

    @FindBy(xpath = "//img[@class='icon-freestyle-project icon-xlg']")
    WebElement FreestyleProject;

    @FindBy(xpath = "//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")
    WebElement btnOK;

    @FindBy(id = "name")
    WebElement NewItemName;

    public ItemPage NewItemName() {
        NewItemName.sendKeys("NewItemName");
        return this;
    }

    public ItemPage btnOKClick() {
        btnOK.click();
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
}
