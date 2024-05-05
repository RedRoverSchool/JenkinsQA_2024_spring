package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ViewMyListConfigPage extends BasePage {

    @FindBy(name = "Submit")
    WebElement okButton;

    @FindBy(css = "div.jenkins-dropdown button:last-child")
    WebElement projectDescriptionFromDropdown;

    public ViewMyListConfigPage(WebDriver driver) { super(driver); }

    public ViewMyListConfigPage clickProjectName(String projectName) {
        getDriver().findElement(By.cssSelector("label[title=" + projectName + "]")).click();

        return this;
    }

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public ViewMyListConfigPage clickAddColumn() {
        WebElement addColumn = getDriver().findElement(By.cssSelector("[suffix='columns']>svg"));
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", addColumn);
        addColumn.click();

        return this;
    }

    public ViewMyListConfigPage clickColumnName() {
        projectDescriptionFromDropdown.click();

        return this;
    }

    public ViewMyListConfigPage selectProjectForAddToView(String name) {
        clickElement(getDriver().findElement(
                By.xpath("//label[contains(@title, '" + name + "')]")));
        return this;
    }

}
