package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseProjectPage<T extends BaseProjectPage<T>> extends BasePage<T> {

    @FindBy(tagName = "h1")
    private WebElement projectName;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

}
