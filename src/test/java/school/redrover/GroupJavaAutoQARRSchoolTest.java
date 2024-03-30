package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupJavaAutoQARRSchoolTest extends BaseTest {

    @Test
    public void testReturnToHomePageFromInventoryDetailsCard() {

        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.name("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.name("login-button")).click();

        WebElement inventoryName = driver.findElement(By.xpath("//div[text() = 'Sauce Labs Bike Light']"));
        inventoryName.click();

        WebElement backToProductBtn = driver.findElement(By.xpath("//button[@id = 'back-to-products']"));
        backToProductBtn.click();

        WebElement headerProductsOnTheHomePage = driver.findElement(By.xpath("//span[text() = 'Products']"));
        String value = headerProductsOnTheHomePage.getText();

        Assert.assertEquals(value, "Products");
    }
}
