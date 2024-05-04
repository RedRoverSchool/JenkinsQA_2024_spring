package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import school.redrover.model.AppearancePage;
import school.redrover.model.HomePage;

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
}
