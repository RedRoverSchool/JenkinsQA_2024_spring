package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderLogTest extends BaseTest {
    @Test
    public void testCreateOrganizationFolder() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Organization folder");
        getDriver().findElement(By.xpath(
                "//*[contains(@class, 'OrganizationFolder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test(dependsOnMethods = "testCreateOrganizationFolder")
    public void testScanOrganizationFolder(){
        getDriver().findElement(By.xpath(
                "//*[@href='job/New%20Organization%20folder/']/span")).click();
        getDriver().findElement(By.xpath("//*[contains(@href,'console')]")).click();

        Assert.assertNotNull(getDriver().findElements(By.xpath(
                "//*[contains(@class,'lds-ellipsis')]/div[1]")));
    }
}
