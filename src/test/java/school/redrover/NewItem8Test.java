package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem8Test extends BaseTest {

    private final String itemName = "My first Multibranch Pipeline";

    @Test
    public void testCreateItem() {
        getDriver().findElement(By.className("task")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(itemName);
        getDriver().findElement(By.xpath("(//li[@tabindex='0'])[5]")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(itemName)).isDisplayed());
    }

    @Test
    public void testCreateItemFromNewJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(itemName);
        getDriver().findElement(By.xpath("(//li[@tabindex='0'])[5]")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(itemName)).isDisplayed());
    }
}