package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage {

    @FindBy(id = "main-panel")
    private WebElement fullProjectName;

    @FindBy(xpath = "//*[@id='main-panel']//h1")
    private WebElement projectName;

    @FindBy (css = "#description > div:first-child")
    private WebElement projectDescription;

    @FindBy(xpath = "//a[contains(@href, 'confirm-rename')]")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@id = 'tasks']/div[6]/span")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public String checkFullProjectName() {

        return fullProjectName.getText();
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public String getProjectDescriptionText() {
        return projectDescription.getText();
    }

    public FreestyleRenamePage clickRename() {

        renameButton.click();

        return new FreestyleRenamePage(getDriver());
    }

    public FreestyleProjectPage deleteFreestyleProject (){
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        return this;
    }

    public HomePage confirmDeleteFreestyleProject(){
        yesButton.click();
        return new HomePage(getDriver());
    }
}