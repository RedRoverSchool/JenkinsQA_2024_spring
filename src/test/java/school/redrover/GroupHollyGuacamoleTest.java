package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class GroupHollyGuacamoleTest extends BaseTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String CART_PAGE = "//a[@class='shopping_cart_link']";
    private static final String BACKPACK_REMOVE_BUTTON = "//button[@id='remove-sauce-labs-backpack']";
    private void login(){
        getDriver().get(BASE_URL);
        getDriver().findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath("//input[@id='login-button']")).click();
    }

    @Test
    private void testLoginSuccess(){
        String expectedResult = BASE_URL + "inventory.html";
        login();
        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    private void testCheckAmountItems() throws InterruptedException {
        int expectedResult = 6;
        login();
        Thread.sleep(1000);
        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='inventory_item']"));
        int actualResult = items.size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    private void testAddItemToTheCard(){
        boolean expectedResult = true;
        login();
        getDriver().findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']")).click();
        getDriver().findElement(By.xpath(CART_PAGE)).click();
        boolean actualResult = getDriver().findElement(By.linkText("Sauce Labs Bike Light")).isDisplayed();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    private void testRemoveItemFromTheCart(){
        login();
        getDriver().findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        getDriver().findElement(By.xpath(CART_PAGE)).click();
        getDriver().findElement(By.linkText("Sauce Labs Backpack")).isDisplayed();
        getDriver().findElement(By.xpath(BACKPACK_REMOVE_BUTTON)).click();
        boolean yestCho;
        yestCho = getDriver().findElements(By.xpath(BACKPACK_REMOVE_BUTTON)).isEmpty();

        Assert.assertTrue(yestCho);
    }
}
