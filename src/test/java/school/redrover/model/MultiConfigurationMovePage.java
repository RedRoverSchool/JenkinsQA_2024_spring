package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BasePage;

public class MultiConfigurationMovePage extends BasePage<MultiConfigurationMovePage> {

    @FindBy(name = "destination")
    private WebElement selectDestination;

    @FindBy(name = "Submit")
    private WebElement moveButton;

    public MultiConfigurationMovePage(WebDriver driver) {
        super(driver);
    }

    @Step("Select a Folder")
    public MultiConfigurationMovePage selectFolder(String folderName) {
        new Select(selectDestination).selectByValue("/" + folderName);

        return this;
    }

    @Step("Click on 'Move' ")
    public MultiConfigurationProjectPage clickMove() {
        moveButton.click();

        return new MultiConfigurationProjectPage(getDriver());
    }
}
