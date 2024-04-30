package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreeStyleProjectDSTest  extends BaseTest {

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreateFreeStyleProjectDS () {
        final String projectName = "FreeStyleProjectDS";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        openDashboard();
        Assert.assertTrue(getDriver().findElement(By.linkText(projectName)).isDisplayed());
    }
}
