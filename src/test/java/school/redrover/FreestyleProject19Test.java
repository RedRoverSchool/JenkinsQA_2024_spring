package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject19Test extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {

        String myFreestyleProjectName = "Ivan’s Freestyle Project";

        getDriver().findElement(By.linkText("New Item")).click();
        WebElement nameElement = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        nameElement.sendKeys(myFreestyleProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String pageConfigure = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(pageConfigure, "Configure", "Page Configure is not displayed");

        getDriver().findElement(By.linkText("Dashboard")).click();

        WebElement newProject = getDriver().findElement(By.linkText(myFreestyleProjectName));
        Assert.assertTrue(newProject.isDisplayed(), "New project is not displayed");

    }
}
