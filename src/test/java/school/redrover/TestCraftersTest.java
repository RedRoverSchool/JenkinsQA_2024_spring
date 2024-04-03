//This class for @test_crafters group.
//URL for test: https://tutorialsninja.com/demo/

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestCraftersTest extends BaseTest {
    private final static String URL = "https://tutorialsninja.com/demo/";

    @Test
    public void testMainPageView() {
        initialization(URL);
        String featered = getDriver().findElement(By.xpath("//h3")).getText();

        Assert.assertEquals(featered, "Featured");

    }
    // Header test
//Site currency tests
    @Test(description = "default currency must be $")
    public void testDefaultCurrency() {
        initialization(URL);
        String currentCurrency = getDriver().findElement(By.xpath("//strong")).getText();
        List<WebElement> itemPrices = getDriver().findElements(By.className("price"));

        Assert.assertEquals(currentCurrency, "$");

        for (WebElement price: itemPrices) {
            String exTax = new StringBuilder(price.getText().substring(price.getText().indexOf(":")+1)).toString();
            Assert.assertEquals(price.getText().charAt(0), '$');
            Assert.assertEquals(exTax.charAt(0), '$');

        }
    }

    @Test
    public void testPound() {
        initialization(URL);
        getDriver().findElement(By.id("form-currency")).click();
        getDriver().findElement(By.xpath("//*[@name='GBP']")).click();
        String currentCurrency = getDriver().findElement(By.xpath("//strong")).getText();
        List<WebElement> itemPrices = getDriver().findElements(By.className("price"));

        Assert.assertEquals(currentCurrency, "£");

        for (WebElement price: itemPrices) {
            String exTax = new StringBuilder(price.getText().substring(price.getText().indexOf(":")+1)).toString();

            Assert.assertEquals(price.getText().charAt(0), '£');
            Assert.assertEquals(exTax.charAt(0), '£');
        }
    }

    @Test
    public void testEuro() {
        initialization(URL);
        getDriver().findElement(By.id("form-currency")).click();
        getDriver().findElement(By.xpath("//*[@name='EUR']")).click();
        String currentCurrency = getDriver().findElement(By.xpath("//strong")).getText();
        List<WebElement> itemsPrice = getDriver().findElements(By.className("price"));

        Assert.assertEquals(currentCurrency, "€");

        for (WebElement price: itemsPrice) {
            String exTax = new StringBuilder(price.getText().substring(price.getText().indexOf(":")+1)).toString();
            String totalPrice = new StringBuilder(price.getText().substring(0, price.getText().indexOf("€")+1)).toString();

            Assert.assertTrue(totalPrice.contains("€"));
            Assert.assertTrue(exTax.contains("€"));
        }


    }
    //tests phone

    @Test
    public void testPhoneIcoRedirect() {
        initialization(URL);
        getDriver().findElement(By.xpath("//i[@class='fa fa-phone']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(),"https://tutorialsninja.com/demo/index.php?route=information/contact");
    }

    @Test
    public void testPhoneNumber() {
        initialization(URL);
        String phoneNumber = getDriver().findElement(By.xpath("//*[@id='top-links']/ul/li[1]/span")).getText();

        Assert.assertEquals(phoneNumber, "123456789");
    }

    // tests My account

    @Test
    public void testHeaderMyAccountRegisterRedirect() {
        initialization(URL);
        getDriver().findElement(By.xpath("//li[@class='dropdown']//span[contains(text(),'My Account')]")).click();
        getDriver().findElement(By.xpath("//*[contains(text(),'Register')]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://tutorialsninja.com/demo/index.php?route=account/register");
    }

    @Test
    public void testHeaderLoginRedirect() {
        initialization(URL);
        getDriver().findElement(By.xpath("//li[@class='dropdown']//span[contains(text(),'My Account')]")).click();
        getDriver().findElement(By.xpath("//*[contains(text(),'Login')]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://tutorialsninja.com/demo/index.php?route=account/login");
    }
    //test whish list
    @Test(description = "without login")
    public void testWishListRedirec() {
        initialization(URL);
        getDriver().findElement(By.xpath("//*[@class='fa fa-heart']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://tutorialsninja.com/demo/index.php?route=account/login");
    }

    //tests Shoping cart

    @Test
    public void testShopingCartRedirect() {
        initialization(URL);
        getDriver().findElement(By.xpath("//*[contains(text(),'Shopping Cart')]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://tutorialsninja.com/demo/index.php?route=checkout/cart");
    }

    //tests Checkout
    @Test
    public void testCheckoutRedirect() {
        initialization(URL);
        getDriver().findElement(By.xpath("//*[contains(text(),'Checkout')]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://tutorialsninja.com/demo/index.php?route=checkout/cart");
    }
    @Test
    public void testHomePageTitle(){
        initialization(URL);
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "Your Store";
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void testRegister() {
        getDriver().get(URL);

        getDriver().findElement(By.linkText("My Account")).click();
        getDriver().findElement(By.linkText("Register")).click();
        getDriver().findElement(By.id("input-firstname")).sendKeys("TestFirstName2");
        getDriver().findElement(By.id("input-lastname")).sendKeys("TestLastName2");
        getDriver().findElement(By.id("input-email")).sendKeys("Kattin@gmail.com");
        getDriver().findElement(By.id("input-telephone")).sendKeys("1234567891");
        getDriver().findElement(By.id("input-password")).sendKeys("12345test");
        getDriver().findElement(By.id("input-confirm")).sendKeys("12345test");
        getDriver().findElement(By.name("agree")).click();
        getDriver().findElement(By.xpath("//input[@class= 'btn btn-primary']")).click();

        String actualTitle = getDriver().getTitle();
        String expectedTitle = "Your Account Has Been Created!";
        Assert.assertEquals(actualTitle, expectedTitle);
    }




    public void initialization(String url) {
        getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        getDriver().get(url);

    }
}
