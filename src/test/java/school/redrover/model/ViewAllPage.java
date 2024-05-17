package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class ViewAllPage extends BasePage {
    public ViewAllPage(WebDriver driver) { super(driver); }

    public FolderProjectPage clickBreadcrumbAll() {
        getDriver().findElement(By.xpath("(//li[@class='children'])[2]")).click();

        return new FolderProjectPage(getDriver());
    }


}
