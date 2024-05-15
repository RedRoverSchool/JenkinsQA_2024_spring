package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class SearchResultPage extends BasePage {

    @FindBy(css = "[class='error']")
    private WebElement noMatchResult;

    @FindBy(css = "[id*='page'] h1")
    private WebElement matchLogResult;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }
    public String getNoMatchText() {
        return noMatchResult.getText();
    }

    public String getMatchLogResult() {
        return matchLogResult.getText();
    }
}
