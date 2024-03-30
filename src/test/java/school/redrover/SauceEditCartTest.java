package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
public class SauceEditCartTest extends SauceSuccessLoginTest{
    String cartPage = "//a[@class='shopping_cart_link']";
    String backpackRemoveButton = "//button[@id='remove-sauce-labs-backpack']";
    @Test
    public void testAddItemToTheCard(){
        boolean expectedResult = true;
        login();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']")).click();
        driver.findElement(By.xpath(cartPage)).click();
        boolean actualResult = driver.findElement(By.linkText("Sauce Labs Bike Light")).isDisplayed();
        Assert.assertEquals(actualResult, expectedResult);
    }
    @Test
    public void testRemoveItemFromTheCart(){
        //Preconditions
        login();
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath(cartPage)).click();
        driver.findElement(By.linkText("Sauce Labs Backpack")).isDisplayed();
        //Test
        driver.findElement(By.xpath(backpackRemoveButton)).click();
        boolean yestCho;
        try{
            driver.findElement(By.xpath(backpackRemoveButton));
            yestCho = true;
        } catch (Exception e){
            yestCho = false;
        }
        Assert.assertFalse(yestCho);
    }
}