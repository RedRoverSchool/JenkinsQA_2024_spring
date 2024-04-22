package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject9Test extends BaseTest {
    private final String freestyleProjectName = "Freestyle Project";
    private final String expectedFreestyleProjectDescription = "This is very important freestyle project";

    public void createFreestyleProjectWithoutDescription() {
        getDriver().findElement(By.cssSelector
                ("#tasks > div:nth-child(1) > span > a > span.task-icon-link > svg > path")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector
                ("#j-add-item-type-standalone-projects > ul > li.hudson_model_FreeStyleProject > div.desc")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector
                ("#bottom-sticker > div > button.jenkins-button.jenkins-button--primary")).click();
        getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(1) > a")).click();
    }

    public void createFreestyleProjectWithDescription() {
        getDriver().findElement(By.cssSelector
                ("#tasks > div:nth-child(1) > span > a > span.task-icon-link > svg > path")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector
                ("#j-add-item-type-standalone-projects > ul > li.hudson_model_FreeStyleProject > div.desc")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector
                        ("#main-panel > form > div.config-table > div.jenkins-section.jenkins-section--no-border.jenkins-\\!-margin-top-6 > div > div.setting-main > textarea")).
                sendKeys(expectedFreestyleProjectDescription);
        getDriver().findElement(By.cssSelector
                ("#bottom-sticker > div > button.jenkins-button.jenkins-button--primary")).click();
    }
    @Test
    public void testCreateFreestyleProjectWithoutDescription() {
        createFreestyleProjectWithoutDescription();
        Assert.assertTrue(getDriver().findElement(By.linkText(freestyleProjectName)).isDisplayed());
    }

    @Ignore
    @Test
    public void testCreateFreestyleProjectWithDescription() {
        createFreestyleProjectWithDescription();
        WebElement freestyleProjectDescription = getDriver().findElement(By.cssSelector
                ("#description > div:nth-child(1)"));
        String actualFreestyleProjectDescription = freestyleProjectDescription.getText();
        Assert.assertEquals(actualFreestyleProjectDescription, expectedFreestyleProjectDescription);
    }

    @Test
    public void testOpenFreestyleProject() {
        createFreestyleProjectWithoutDescription();
        getDriver().findElement(By.linkText(freestyleProjectName)).click();
        WebElement pageHeading = getDriver().findElement(By.cssSelector("#main-panel > div.jenkins-app-bar > div > h1"));
        String actualPageHeading = pageHeading.getText();
        Assert.assertEquals(actualPageHeading, freestyleProjectName);
    }
}
