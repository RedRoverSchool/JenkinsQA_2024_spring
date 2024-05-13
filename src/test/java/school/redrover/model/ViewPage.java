package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class ViewPage extends BasePage {

    @FindBy(linkText = "Edit View")
    private WebElement editViewButton;

    @FindBy(css = "div.tab.active")
    private WebElement activeViewName;

    @FindBy(xpath = "//td/a[contains(@href, 'job/')]")
    private List<WebElement> projectNamesList;

    @FindBy(className = "sortheader")
    private List<WebElement> columnNameList;

    @FindBy(xpath = "//a[@data-title='Delete View']")
    private WebElement deleteViewButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement confirmButton;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ViewMyListConfigPage clickEditViewOnSidebar() {
        editViewButton.click();

        return new ViewMyListConfigPage(getDriver());
    }

    public HomePage clickDeleteViewSideBarAndConfirmDeletion() {
        deleteViewButton.click();
        getWait5().until(ExpectedConditions.elementToBeClickable(confirmButton)).click();

        return new HomePage(getDriver());
    }

    public String getActiveViewName() {
        return activeViewName.getText();
    }

    public List<String> getProjectNames() {

        return projectNamesList.stream().map(WebElement::getText).toList();
    }

    public int getProjectListSize() {

        return projectNamesList.size();
    }

    public int getSizeColumnList() {
        return columnNameList.size();
    }

    public List<String> getColumnNameText() {

        return columnNameList.stream().map(WebElement::getText).toList();
    }

    public List<String> getProjectViewTitleList() {
        List<String> actualPipelineViewList = new ArrayList<>();
        List<WebElement> projectViewTitles =
                getWait10().until(ExpectedConditions.visibilityOfAllElements(columnNameList));
        for (WebElement headerTitle : projectViewTitles) {
            String header = headerTitle.getText();
            if (!header.isEmpty()) {
                actualPipelineViewList.add(header);
            }
        }

        return actualPipelineViewList;
    }
}

