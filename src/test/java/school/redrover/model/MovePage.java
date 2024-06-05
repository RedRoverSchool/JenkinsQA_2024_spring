package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class MovePage extends BasePage<MovePage> {

    @FindBy(name = "Submit")
    private WebElement moveButton;

    @FindBy(name = "destination")
    private WebElement selectDestination;

    public MovePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectPage chooseFolderAndConfirmMove(String folderName) {

        Select selectDefinition = new Select(selectDestination);
        selectDefinition.selectByValue("/" + folderName);
        moveButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    public FolderProjectPage chooseDestinationFromListAndMove(String destination) {
        new Select(selectDestination)
                .selectByValue("/" + destination);
        moveButton.click();

        return new FolderProjectPage(getDriver());
    }
}
