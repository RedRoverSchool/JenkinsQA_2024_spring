package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ViewPage extends BasePage {

    @FindBy(linkText = "Edit View")
    WebElement editViewButton;

    public ViewPage(WebDriver driver) { super(driver); }

    public ViewMyListConfigPage clickEditViewButton() {
        editViewButton.click();

        return new ViewMyListConfigPage(getDriver());

    }
}
