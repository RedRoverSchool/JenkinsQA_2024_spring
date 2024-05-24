package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FullStageViewPage extends BasePage {

    @FindBy(xpath = "//h2")
    private WebElement H2HeadingText;

    @FindBy(id = "breadcrumbBar")
    private WebElement breadcrumbBarText;

    @FindBy(className = "badge")
    private List<WebElement> itemList;

    public FullStageViewPage(WebDriver driver) {
        super(driver);
    }

    public String getH2HeadingText() {
        return getWait5().until(ExpectedConditions.visibilityOf(H2HeadingText)).getText();
    }

    public String getBreadcrumbsText() {

        return breadcrumbBarText.getText().replaceAll("\n", " > ");
    }

    public List<String> getItemList() {
        return itemList
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}