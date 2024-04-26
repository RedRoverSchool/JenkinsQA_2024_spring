package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem17Test extends BaseTest {

    @Test
    public void testCreateNewItemFP () {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestItemFor");
        getDriver().findElement(By.xpath("//li[contains(@class, '_FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String configurationHeaderH1 = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals( configurationHeaderH1, "Configure");

        String configurationPageBreadcrumbs = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]")).getText();
        Assert.assertEquals(configurationPageBreadcrumbs, "Configuration");
    }

    @Ignore
    @Test (dependsOnMethods = "testCreateNewItemFP")
    public void testConfigurationPage () {
        String sourceCodeManagementBlock = getDriver().findElement(By.xpath("//*[@id='source-code-management']")).getText();
        Assert.assertEquals(sourceCodeManagementBlock, "Source Code Management");

    }
}
