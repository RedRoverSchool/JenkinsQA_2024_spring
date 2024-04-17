package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem8Test extends BaseTest {

    @Test
    public void testCreateItem() {
        final String itemName = "My first Multibranch Pipeline";

        getDriver().findElement(By.className("task")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(itemName);
        getDriver().findElement(By.xpath("(//li[@tabindex='0'])[5]")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(itemName)).isDisplayed());
    }
}