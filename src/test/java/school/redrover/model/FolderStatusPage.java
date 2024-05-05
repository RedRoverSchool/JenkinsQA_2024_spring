package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "[class*='breadcrumbs']>[href*='job']")
    private WebElement breadcrumbsName;

    @FindBy(css = "[href*='confirm-rename']")
    private WebElement renameButton;

    @FindBy(css = "h1")
    private WebElement pageTopic;

    @FindBy(css = ".empty-state-section")
    private WebElement emptyStateSection;

    @FindBy(css = "tr > td > .jenkins-table__link > span:first-child")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//a[.='New Item']")
    private WebElement newItem;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbName() {
        return breadcrumbsName.getText();
    }

    public FolderRenamePage clickOnRenameButton() {
        renameButton.click();

        return new FolderRenamePage(getDriver());
    }

    public String getPageTopic() {
        return pageTopic.getText();
    }

    public Boolean isFolderEmpty() {
        return emptyStateSection.isDisplayed();
    }

    public CreateNewItemPage clickNewItemInsideFolder() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    public List<String> getItemListInsideFolder() {
        return itemsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
