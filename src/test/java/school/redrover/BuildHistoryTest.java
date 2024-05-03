package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BuildHistoryTest extends BaseTest{
    private final String PROJECT_NAME = "My freestyle project";

    @Test
    public void testCreatFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);

        getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Classic, general-purpose job')]"))).click();

        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='Submit']"))).click();

        String actualMyProject = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-app-bar']"))).getText();

        Assert.assertEquals(actualMyProject, PROJECT_NAME);
        }

        @Test(dependsOnMethods = "testCreatFreestyleProject")
        public void testGetTableBuildHistory() {
        getDriver().findElement(By.xpath("//a[contains(@tooltip,'Schedule a Build')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        String actualTableTitle = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))).getText();

        Assert.assertEquals(actualTableTitle, "Build History of Jenkins");
    }
}