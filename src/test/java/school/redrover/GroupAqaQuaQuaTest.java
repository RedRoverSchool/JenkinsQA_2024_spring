package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class GroupAqaQuaQuaTest extends BaseTest {

    @Test
    public void testyAddingBookToCart() {
        getDriver().get("https://demowebshop.tricentis.com/");


        WebElement books = getDriver().findElement(By.xpath("//ul[@class='top-menu']//a[@href='/books']"));
        books.click();

        WebElement myBook = getDriver().findElement(By.xpath("//div[@class = 'details']//a[@href= '/computing-and-internet']"));
        myBook.click();

        WebElement addMyBook = getDriver().findElement(By.id("add-to-cart-button-13"));
        addMyBook.click();

        WebElement shoppingCart = getDriver().findElement(By.className("cart-label"));
        shoppingCart.click();


        Assert.assertEquals(getDriver().findElement(By.className("product-name")).getText(), "Computing and Internet");

    }
    @Test
    public void testLoginTheInternet() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/");

        Assert.assertEquals(driver.getTitle(), "The Internet");

        driver.findElement(By.linkText("Form Authentication")).click();

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button")).click();

        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "Secure Area");
//
//        driver.findElement(By.linkText("Logout")).click();
//        driver.findElement(By.tagName("h2")).getText();
//
//        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "Login Page");
    }
}


