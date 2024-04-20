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
}
