package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ListViewConfigPage extends BasePage<ListViewConfigPage> {

    @FindBy(name = "Submit")
    private WebElement okButton;

    @FindBy(css = "[descriptorid$='DescriptionColumn'] .dd-handle")
    private WebElement projectDescriptionColumnHandle;

    @FindBy(css = "[descriptorid$='StatusColumn']")
    private WebElement statusColumn;

    public ListViewConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click checkbox before '{projectName} for including it to this View")
    public ListViewConfigPage clickCheckboxWithJobName(String jobName) {
        WebElement jobOnConfigurePage = getDriver().findElement(By.cssSelector("label[title=" + jobName + "]"));
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,250)", "");
        jobOnConfigurePage.click();

        return this;
    }

    @Step("Click 'Ok' button for saving configuration")
    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    @Step("Click 'Add column' button")
    public ListViewConfigPage clickAddColumn() {
        WebElement addColumn = getDriver().findElement(By.cssSelector("[suffix='columns']>svg"));
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", addColumn);
        addColumn.click();

        return this;
    }

    @Step("Select and click on column name {columnName}")
    public ListViewConfigPage selectAndClickOnColumnName(String columnName) {
        getDriver().findElement(By.xpath("//button[contains(text(),'" + columnName + "')]")).click();

        return this;
    }

    @Step("Select the checkbox with the label Title '{title}'")
    public ListViewConfigPage checkProjectForAddingToView(String title) {
        clickElementFromTheBottomOfThePage(getDriver().findElement(
                By.xpath("//label[contains(@title, '" + title + "')]")));
        return this;
    }

    @Step("Scroll to 'Ok' button")
    public ListViewConfigPage scrollToOkButton() {
        scrollToElement(okButton);

        return this;
    }

    @Step("Scroll to column name '{columnName}'")
    public ListViewConfigPage scrollToColumnName(String columnName) {
        scrollToElement(getDriver().findElement(By.xpath(
                "//div[contains(text(),'" + columnName + "')]")));

        return this;
    }

    @Step("Click and hold mouse cursor on column 'Project description' and move it to column 'Status'")
    public ListViewConfigPage drugAndDropDescriptionColumnToStatusColumn() {
        Actions actions = new Actions(getDriver());
        actions.clickAndHold(projectDescriptionColumnHandle).moveByOffset(30, -100)
                .perform();

        actions.moveByOffset(100, -100).perform();
        actions.release().perform();

        new Actions(getDriver())
                .dragAndDrop(projectDescriptionColumnHandle, statusColumn)
                .perform();
        return this;
    }
}
