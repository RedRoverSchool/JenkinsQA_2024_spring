package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class DraftPage extends BasePage {

    public DraftPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    WebElement ElementPeople;

    @FindBy(xpath = "//h1[contains(.,'Welcome to Jenkins!')]")
    WebElement ElementWelcome;

    public DraftPage ElementPeople() {
        ElementPeople.click();
        return this;
    }

    public DraftPage ElementWelcome() {
        ElementWelcome.click();
        return this;
    }
}
