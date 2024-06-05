package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class FreestyleMovePage extends BasePage<FreestyleMovePage> {

    @FindBy(xpath = "//select[@name='destination']")
    private WebElement dropDownDestinationPath;
    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement moveButton;

    public FreestyleMovePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleMovePage choosePath(String folderName) {

        Select simpleDropDown = new Select(dropDownDestinationPath);
        simpleDropDown.selectByValue("/" + folderName);

        return this;
    }

    public FreestyleProjectPage clickMoveButton() {
        moveButton.click();

        return new FreestyleProjectPage(getDriver());
    }


}
