package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class MovePage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement selectDestination;

    public MovePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectPage chooseFolderAndSave(String folderName) {

        Select selectDefinition = new Select(selectDestination);
        selectDefinition.selectByValue("/" + folderName);
        moveButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    public HomePage chooseFolderFromListAndSave(String folderName) {
        new Select(selectDestination)
                .selectByValue("/" + folderName);
        moveButton.click();
        clickLogo();
        return new HomePage(getDriver());
    }

    public FolderProjectPage chooseDestinationFromListAndSave(String destination) {
        new Select(selectDestination)
                .selectByValue("/" + destination);
        moveButton.click();

        return new FolderProjectPage(getDriver());
    }
}
