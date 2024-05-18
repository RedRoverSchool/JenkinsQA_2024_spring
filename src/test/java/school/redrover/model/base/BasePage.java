package school.redrover.model.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.AppearancePage;
import school.redrover.model.FreestylePage;
import school.redrover.model.HomePage;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePage extends BaseModel {

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

    public AppearancePage resetJenkinsTheme() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();

        WebElement defaultThemeButton = getDriver().findElement(By.cssSelector("[for='radio-block-2']"));
        if (!defaultThemeButton.isSelected()) {
            defaultThemeButton.click();
            getDriver().findElement(By.name("Apply")).click();
        }
        return new AppearancePage(getDriver());
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public FreestylePage triggerJobViaHTTPRequest(String token, String user, String projectName) {
        final String postBuildJob = "http://" + user + ":" + token + "@localhost:8080/job/Project1/build?token=" + projectName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildJob);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));

        return new FreestylePage(getDriver());
    }

    public void revokeTokenViaHTTPRequest(String token, String uuid, String user) {
        final String postRevokeToken = "http://" + user + ":" + token + "@localhost:8080/user/" + user
                + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

}
