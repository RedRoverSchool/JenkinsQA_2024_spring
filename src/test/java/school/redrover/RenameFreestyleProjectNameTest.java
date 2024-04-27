package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameFreestyleProjectNameTest extends BaseTest {
    private void createFreestyleProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys("OldFreestyleProjectName");
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRenameFreestyleProjectName() throws InterruptedException {
        final String expectedProjectName = "New Freestyle Project";

        createFreestyleProject();
        openDashboard();

        WebElement nameProject = getDriver().findElement(By.xpath("//span[normalize-space()='OldFreestyleProjectName']"));

        Actions action = new Actions(getDriver());

        WebElement projectLink = getDriver().findElement(By.linkText("OldFreestyleProjectName"));
        action.moveToElement(nameProject).perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-menu-dropdown-chevron[data-href='http://localhost:8080/job/OldFreestyleProjectName/']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class ='jenkins-dropdown__item'][4]"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Rename Project OldFreestyleProjectName");

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("OldFreestyleProjectNameNew");

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "OldFreestyleProjectNameNew");
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "Permalinks");
    }
}