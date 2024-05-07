package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class ViewPage extends BasePage {

    @FindBy(linkText = "Edit View")
    WebElement editViewButton;

    @FindBy(css = "div.tab.active")
    WebElement activeViewName;

    @FindBy(xpath = "//td/a[contains(@href, 'job/')]")
    List<WebElement> projectNamesList;

    public ViewPage(WebDriver driver) { super(driver); }

    public ViewMyListConfigPage clickEditViewButton() {
        editViewButton.click();

        return new ViewMyListConfigPage(getDriver());
    }

    public String getActiveViewName() {
        return activeViewName.getText();
    }

    public List<String> getProjectNames(){
        return projectNamesList.stream().map(WebElement::getText).toList();
    }

    public int sizeColumnList() {

        return getDriver().findElements(By.className("sortheader")).size();
    }
}

