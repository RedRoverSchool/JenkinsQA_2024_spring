package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import school.redrover.runner.TestUtils;

public class NewItem17Test extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectDG";

    @Test
    public void testCreateNewItemFP() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, '_FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String configurationHeaderH1 = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(configurationHeaderH1, "Configure");

        String configurationPageBreadcrumbs = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]")).getText();
        Assert.assertEquals(configurationPageBreadcrumbs, "Configuration");

        String sourceCodeManagementBlock = getDriver().findElement(By.xpath("//*[@id='source-code-management']")).getText();
        Assert.assertEquals(sourceCodeManagementBlock, "Source Code Management");

    }

    @Test
    public void testCreateNewItemFPTU() {
        TestUtils.createNewItem(this,PROJECT_NAME, TestUtils.Item.FREESTYLE_PROJECT);
        getDriver().findElement(By.name("Submit")).click();

        String configurationHeaderH1 = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(configurationHeaderH1, PROJECT_NAME);
    }
}
