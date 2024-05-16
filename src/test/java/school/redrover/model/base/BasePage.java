package school.redrover.model.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.HomePage;
import school.redrover.model.SearchResultPage;
import school.redrover.model.UserPage;

import java.util.List;

public abstract class BasePage extends BaseModel {

    @FindBy(css = "a.model-link > span")
    private WebElement userNameOnHeader;

    @FindBy(css = ".jenkins-button.jenkins-button--tertiary.rest-api")
    private WebElement restapiLink;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = "[class$=jenkins_ver]")
    private WebElement version;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickLogo() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        return new HomePage(getDriver());
    }

    public void openElementDropdown(WebElement element) {
        WebElement chevron = element.findElement(By.cssSelector("[class $= 'chevron']"));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
    }

    public void openHeaderUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.cssSelector("[data-href$='admin']")))
                .pause(1000)
                .click()
                .perform();
    }

    public boolean isThereTextInBreadcrumbs(String text) {
        return getDriver().findElements(By.className("jenkins-breadcrumbs__list-item"))
                .stream()
                .anyMatch(e -> e.getText()
                .contains(text));
    }

    public void hoverOverElement(WebElement element) {
        new Actions(getDriver())
                .moveToElement(element)
                .perform();
    }

    public void clickSpecificDropdownArrow(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", element);
    }

    protected void clickElement(WebElement webElement) {
        new Actions(getDriver())
                .scrollToElement(webElement)
                .scrollByAmount(0, 100)
                .moveToElement(webElement)
                .click().perform();
    }

    public boolean areElementsEnabled(List<WebElement> elements) {
        return elements
                .stream()
                .allMatch(WebElement::isEnabled);
    }

    public String getText(WebElement webElement) {
        return webElement.getText();
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", element);
    }

    public UserPage clickUserNameOnHeader() {
        userNameOnHeader.click();

        return new UserPage(getDriver());
    }

    public String getRestApiLinkColor() { return restapiLink.getCssValue("color"); }

    public <T extends BaseProjectPage> T searchProjectByName(String projectName, T projectType) {
        searchBox.sendKeys(projectName + Keys.ENTER);

        return projectType;
    }

    public SearchResultPage typeTextToSearchBox(String text) {
        searchBox.sendKeys(text + Keys.ENTER);

        return new SearchResultPage(getDriver());
    }
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getVersionOnFooter() {
        return version.getText().split(" ")[1];
    }

    public HomePage clickVersion() {
        version.click();

        return new HomePage(getDriver());
    }

}
