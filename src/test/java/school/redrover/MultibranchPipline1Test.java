package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipline1Test extends BaseTest {

    @Ignore
    @Test
    public void testCreate() {

        final String MULTIBRANCH_NAME = "Vika Multibranch Pipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowMultiBranchProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(text(),MULTIBRANCH_NAME)]")).isDisplayed(), MULTIBRANCH_NAME);
    }
}
