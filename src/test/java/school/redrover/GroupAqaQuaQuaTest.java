package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.lang.Thread.sleep;


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
    public void testDropDownMenuGiftCards() {
        getDriver().get("https://demowebshop.tricentis.com/");

        WebElement elementDropDownMenu = getDriver().findElement(
                By.xpath("//a[@href='/gift-cards']"));
        elementDropDownMenu.click();

        Assert.assertEquals(getDriver().getTitle(), "Demo Web Shop. Gift Cards");

        WebElement menuOnPageGiftCards = getDriver().findElement((
                By.cssSelector("div.page.category-page>div.page-title>h1")
                ));
        Assert.assertEquals(menuOnPageGiftCards.getText(), "Gift Cards");
    }







































































































































    @Test
    public void testComputersSearch() throws InterruptedException {
        getDriver().get("https://demowebshop.tricentis.com/");

        getDriver().findElement(By.id("small-searchterms")).sendKeys("computer");

        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@Class='button-1 search-box-button']")).click();
        Thread.sleep(2000);

        String resultText = getDriver().findElement(By.cssSelector(".page-title")).getText();

        Assert.assertEquals(resultText, "Search");

    }

}


