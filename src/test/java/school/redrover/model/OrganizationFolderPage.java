package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class OrganizationFolderPage extends BasePage {
    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement jobNameHeading;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getJobNameHeading() {

        return getWait5().until(ExpectedConditions.visibilityOf(jobNameHeading));
    }

    public String getJobNameHeadingText() {

        return getJobNameHeading().getText();
    }
}
