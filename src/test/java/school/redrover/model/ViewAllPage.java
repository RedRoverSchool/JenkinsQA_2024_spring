package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ViewAllPage extends BasePage<ViewAllPage> {

    @FindBy(xpath = "(//li[@class='children'])[2]")
    private WebElement allDropDownChevron;

    public ViewAllPage(WebDriver driver) { super(driver); }

    public FolderProjectPage clickBreadcrumbAll() {
       allDropDownChevron.click();

        return new FolderProjectPage(getDriver());
    }


}
