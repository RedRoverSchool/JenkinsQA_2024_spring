package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ViewConfigPage extends BasePage {


    @FindBy(xpath = "//div[@class='tab active']")
    WebElement newViewName;

    public ViewConfigPage(WebDriver driver) { super(driver); }

    public ViewConfigPage getNewViewName() {
        newViewName.getText();

        return this;
    }
}
