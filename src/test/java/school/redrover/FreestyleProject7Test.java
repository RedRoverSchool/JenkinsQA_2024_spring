package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject7Test extends BaseTest {

    private void createFreestyleProject(String freestyleProjectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void openDashboard(){
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testNewCreateFreestyleProject() {
        final String expectedFreestyleProjectName = "FreestyleProjectTest";
        final String expectedStateMessage = "Permalinks";

        createFreestyleProject(expectedFreestyleProjectName);
        openDashboard();

        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside'] span")).click();

        final String actualFreestyleProjectName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualStateMessage = getDriver().findElement(By.xpath("//div[@id='main-panel']/h2")).getText();

        Assert.assertEquals(actualFreestyleProjectName, expectedFreestyleProjectName);
        Assert.assertEquals(actualStateMessage, expectedStateMessage);
    }
}
