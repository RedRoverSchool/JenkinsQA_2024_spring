package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "h1")
    private WebElement pageTopic;

    @FindBy(css = ".empty-state-section")
    private WebElement emptyStateSection;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    public String getPageTopic() {
        return pageTopic.getText();
    }

    public Boolean isFolderEmpty() {
        return emptyStateSection.isDisplayed();
    }

    public CreateNewItemPage clickNewItemInsideFolder() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemListInsideFolder() {
        return getDriver().findElements(By.cssSelector("tr > td > .jenkins-table__link > span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }


}
