package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderDSTest extends BaseTest {

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreateFolderDS() {
        final String folderName = "FolderDS";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.id("ok-button")).click();
        openDashboard();
        Assert.assertTrue(getDriver().findElement(By.linkText(folderName)).isDisplayed());
    }
}
