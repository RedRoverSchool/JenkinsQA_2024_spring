package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupCarlthefogExpandtestingTest extends BaseTest {
    @Test
    public void testExpandtesting () {

        getDriver().get("https://practice.expandtesting.com/login");

        getDriver().findElement(By.id("username")).sendKeys("practice");
        getDriver().findElement(By.name("password")).sendKeys("SuperSecretPassword!");
        getDriver().findElement(By.xpath("//button[@type='submit' and contains(text(),'Login')]")).click();

        String actual = getDriver().findElement(By.id("flash")).getText();

        Assert.assertEquals(actual, "You logged into a secure area!");
    }
}