package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigPage<T extends BaseProjectPage> extends BasePage {

    private final T projectPage;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public BaseConfigPage(WebDriver driver, T projectPage) {
        super(driver);
        this.projectPage = projectPage;
    }

    public T clickSaveButton() {
        saveButton.click();

        return projectPage;
    }
}
