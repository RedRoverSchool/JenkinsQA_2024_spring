package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class SearchResultPage extends BasePage {

    @FindBy(css = "[class='error']")
    private WebElement noMatchResult;

    @FindBy(css = "[id*='page'] h1")
    private WebElement matchLogResult;

    @FindBy(css = "#main-panel")
    private WebElement mainPanel;

    @FindBy(xpath = "//div[@id='main-panel']//li/a")
    private List<WebElement> searchResultList;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }
    public String getNoMatchText() {
        return noMatchResult.getText();
    }

    public String getMatchLogResult() {
        return matchLogResult.getText();
    }

    public String getTextFromMainPanel() {
        return mainPanel.getText();
    }

    public List<String> getSearchResult() {

        return getWait5().until(ExpectedConditions.visibilityOfAllElements(searchResultList))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
