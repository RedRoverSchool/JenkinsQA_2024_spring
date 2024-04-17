package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem8Test extends BaseTest {
    @Test
    public void testCreateItem() {
        String itemName = "My first Multibranch Pipeline";
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[text()='OK']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(itemName)).isDisplayed());


    }
}
