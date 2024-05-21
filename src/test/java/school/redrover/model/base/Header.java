package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import school.redrover.model.*;

public interface Header {

    default UserPage clickUserNameOnHeader(WebDriver driver) {
        driver.findElement(By.cssSelector("a.model-link > span")).click();

        return new UserPage(driver);
    }

    default SearchResultPage typeTextToSearchBox(WebDriver driver, String text) {
        driver.findElement(By.id("search-box")).sendKeys(text + Keys.ENTER);

        return new SearchResultPage(driver);
    }

    default <T extends BaseProjectPage> T searchProjectByName(WebDriver driver, String projectName, T projectType) {
        driver.findElement(By.id("search-box")).sendKeys(projectName + Keys.ENTER);

        return projectType;
    }

    default void openHeaderUsernameDropdown(WebDriver driver) {
        new Actions(driver)
                .moveToElement(driver.findElement(By.cssSelector("[data-href$='admin']")))
                .pause(1000)
                .click()
                .perform();
    }

    default ViewAllPage clickMyViewsFromDropdown(WebDriver driver) {
        openHeaderUsernameDropdown(driver);
        driver.findElement(By.cssSelector("[href$='admin/my-views']")).click();

        return new ViewAllPage(driver);
    }

    default <T extends BasePage> T clickWarningIcon(T page) {
        page.getDriver().findElement(By.cssSelector("[class$='am-button security-am']")).click();

        return page;
    }

    default String getWarningTooltipText(WebDriver driver) {
        WebElement warningTooltipText = driver.findElement(By.xpath("//div[@role='alert']"));
        return warningTooltipText.getText();
    }

    default SecurityPage clickConfigureTooltipButton(WebDriver driver) {
        driver.findElement(By.xpath("//button[@name='configure']")).click();

        return new SecurityPage(driver);
    }

    default ManageJenkinsPage clickManageJenkinsTooltipLink(WebDriver driver) {
        driver.findElement(By.xpath("//a[contains(text(),'Manage Jenkins')]")).click();

        return new ManageJenkinsPage(driver);
    }
}
