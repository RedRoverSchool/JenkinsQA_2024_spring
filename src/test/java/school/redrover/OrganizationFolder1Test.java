package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertTrue;


public class OrganizationFolder1Test extends BaseTest {

    @Ignore
    @Test
    public void testCreateOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Pipeline syntax");
        getDriver().findElement(By.xpath("//span[contains(text(), 'Organization Folder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement((By.linkText("Dashboard"))).click();
        WebElement projectStatus = getDriver().findElement(By.id("job_Pipeline syntax"));

        assertTrue(projectStatus.isDisplayed());
    }
}
