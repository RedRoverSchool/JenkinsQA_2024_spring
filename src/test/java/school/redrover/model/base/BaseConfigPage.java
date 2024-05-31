package school.redrover.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigPage<
        ProjectPage extends BaseProjectPage<ProjectPage>, ReturnPage extends BasePage<ReturnPage>>
        extends BasePage<ReturnPage> {

    private final ProjectPage projectPage;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public BaseConfigPage(WebDriver driver, ProjectPage projectPage) {
        super(driver);
        this.projectPage = projectPage;
    }

    public ProjectPage clickSaveButton() {
        saveButton.click();

        return projectPage;
    }
}
