package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class WarningIconTest extends BaseTest {

    @Test
    public void testTooltipAccessible() {
        getDriver().findElement(By.cssSelector("[class$='am-button security-am']")).click();
        WebElement warningTooltip = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='alert']")));

        Assert.assertTrue(warningTooltip.getText().contains("Warnings"));
    }

    @Test
    public void testWarningsSettingPage() {
        getDriver().findElement(By.cssSelector("[class$='am-button security-am']")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='configure']"))).click();

        WebElement pageTitle = getDriver().findElement(By.xpath("//h1"));

        Assert.assertTrue(pageTitle.getText().contains("Security"));
    }

    @Test
    public void testAccessToManageJenkinsPage() {
        getDriver().findElement(By.cssSelector("[class$='am-button security-am']")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Manage Jenkins')]"))).click();

        WebElement pageTitle = getDriver().findElement(By.xpath("//h1"));

        Assert.assertTrue(pageTitle.getText().contains("Manage Jenkins"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }
}
