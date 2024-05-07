package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertTrue;


public class OrganizationFolder1Test extends BaseTest {


    @Test
    public void testCreateOrganizationFolder() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.linkText("New Item")).click();
        wait.until(visibilityOfElementLocated(By.id("name"))).sendKeys("Pipeline syntax");
        wait.until(visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Organization Folder')]"))).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        wait.until(visibilityOfElementLocated(By.linkText("Dashboard"))).click();
        WebElement projectStatus = wait.until(visibilityOfElementLocated(By.id("job_Pipeline syntax")));

        assertTrue(projectStatus.isDisplayed());
    }
}
