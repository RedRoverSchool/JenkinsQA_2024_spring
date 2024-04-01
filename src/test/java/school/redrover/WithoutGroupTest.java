package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class WithoutGroupTest extends BaseTest {

    @Test
    public void testLogin() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id='login-button']")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Url does not match expected");

        Assert.assertEquals(driver.findElements(By.xpath("//div[@class='inventory_item']")).size(), 6,
                "Count of cards is not as expected");
    }

}
