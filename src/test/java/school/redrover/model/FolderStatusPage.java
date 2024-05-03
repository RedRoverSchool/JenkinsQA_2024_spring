package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }
}
