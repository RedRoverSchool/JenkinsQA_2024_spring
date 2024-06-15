package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.FooterFrame;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HeaderFrame;
import school.redrover.model.HomePage;
import school.redrover.runner.ProjectUtils;

import java.util.ArrayList;

public abstract class BasePage<T extends BasePage<T>> extends BaseModel {

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(xpath = "//a[@class='main-search__icon-trailing']")
    private WebElement tutorialIcon;

    @FindBy(tagName = "body")
    private WebElement htmlBody;

    @FindBy(id = "jenkins-name-icon")
    private WebElement logo;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the Jenkins logo on the header")
    public HomePage clickLogo() {
        logo.click();

        return new HomePage(getDriver());
    }

    public HeaderFrame<T> getHeader() {
        return new HeaderFrame<>(getDriver(), (T) this);
    }

    public FooterFrame<T> getFooter() {
        return new FooterFrame<>(getDriver(), (T) this);
    }

    public void openElementDropdown(WebElement element) {
        WebElement chevron = element.findElement(By.cssSelector("[class $= 'chevron']"));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
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

    protected void clickElementFromTheBottomOfThePage(WebElement webElement) {
        new Actions(getDriver())
                .scrollToElement(webElement)
                .scrollByAmount(0, 100)
                .moveToElement(webElement)
                .click().perform();
    }

    public String getText(WebElement webElement) {
        return webElement.getText();
    }

    public String getHeadingText() {
        return heading.getText();
    }

    public void scrollIntoViewCenter(WebElement e) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", e);
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", element);
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Step("Trigger remotely '{projectName}' job as user '{user}' with '{tName}' token via HTTPRequest")
    public FreestyleProjectPage triggerJobViaHTTPRequest(String token, String user, String projectName, String tName) {
        final String postBuildJob = "http://" + user + ":" + token + "@" + ProjectUtils.getUrl().substring(7)
                + "job/" + projectName + "/build?token=" + tName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildJob);
        getDriver().switchTo().window((new ArrayList<>(getDriver().getWindowHandles())).get(0));

        return new FreestyleProjectPage(getDriver());
    }

    @Step("Revoke '{user}' token with uuid='{uuid}' via HTTPRequest")
    public void revokeTokenViaHTTPRequest(String token, String uuid, String user) {
        final String postRevokeToken = "http://" + user + ":" + token + "@" + ProjectUtils.getUrl().substring(7)
                + "user/" + user + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        getDriver().switchTo().window((new ArrayList<>(getDriver().getWindowHandles())).get(0));
    }

    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, 0);");
    }

    public static ExpectedCondition<Boolean> isElementInViewPort(WebElement element) {
        return new ExpectedCondition<>() {
            @Override
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (Boolean) js.executeScript(
                        "let rect = arguments[0].getBoundingClientRect();" +
                                "return (rect.top >= 0 && rect.left >= 0 && " +
                                "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                        element);
            }
        };
    }

    public BasePage<T> openTutorial() {
        getWait5().until(ExpectedConditions.visibilityOf(tutorialIcon)).click();

        return this;
    }

    public String getBackgroundColor() {

        return htmlBody.getCssValue("background-color");
    }

    public String getTitle() {
        return getDriver().getTitle();
    }
}
