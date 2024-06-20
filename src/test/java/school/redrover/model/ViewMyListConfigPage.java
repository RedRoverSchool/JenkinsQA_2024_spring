package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

public class ViewMyListConfigPage extends BasePage<ViewMyListConfigPage> {

    @FindBy(name = "Submit")
    private WebElement okButton;

    @FindBy(css = "div.jenkins-dropdown button:last-child")
    private WebElement projectDescriptionFromDropdown;

    @FindBy(css = "[descriptorid$='DescriptionColumn'] .dd-handle")
    private WebElement projectDescriptionColumnHandle;

    @FindBy(css = "[descriptorid$='StatusColumn']")
    private WebElement statusColumn;

    @FindBy(xpath = "//button[contains(text(),' Git Branches')]")
    private WebElement gitBranchesColumn;

    public ViewMyListConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click Project Name on 'ViewListConfigPage'")
    public ViewMyListConfigPage clickProjectName(String projectName) {
      WebElement jobOnConfigurePage =  getDriver().findElement(By.cssSelector("label[title=" + projectName + "]"));
      ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,250)", "");
      jobOnConfigurePage.click();

      return this;
    }

    @Step("Click 'Ok' button on 'ViewMyListConfigPage'")
    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    @Step("Click 'Add column' button on 'ViewMyListConfigPage'")
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

    @Step("Choose 'Git Branches' column from the dropdown menu")
    public ViewMyListConfigPage clickGitBranchColumn() {
        getWait5().until(ExpectedConditions.elementToBeClickable(gitBranchesColumn)).click();

        return this;
    }

    public ViewMyListConfigPage checkProjectForAddingToView(String name) {
        clickElementFromTheBottomOfThePage(getDriver().findElement(
                By.xpath("//label[contains(@title, '" + name + "')]")));
        return this;
    }

    @Step("Scroll to have 'Submit' button on the page")
    public ViewMyListConfigPage scrollIntoSubmit() {
        scrollIntoView(okButton);

        return this;
    }

    public ViewPage moveDescriptionToStatusColumn() {
        new Actions(getDriver())
                .clickAndHold(projectDescriptionColumnHandle)
                .moveToElement(statusColumn)
                .release(statusColumn)
                .build()
                .perform();

        clickOkButton();

        return new ViewPage(getDriver());
    }
}
