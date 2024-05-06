package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class FullStageViewPage extends BasePage {

    @FindBy(xpath = "//h2")
    private WebElement H2HeadingText;

    public FullStageViewPage(WebDriver driver) {
        super(driver);
    }

    public String getH2HeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(H2HeadingText)).getText();
    }
}