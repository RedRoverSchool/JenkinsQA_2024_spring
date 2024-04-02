package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ITboxTest extends BaseTest {
    @Test
    public void checkITboxCart() throws InterruptedException {
        int priceOfCurrentItem;
        int currentValueOfCart = 0;
        List<Integer> actualValueOfCart = new ArrayList<>();
        List<Integer> expectedValueOfCart = new ArrayList<>();

        getDriver().get("https://www.itbox.ua/");
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        getDriver().findElement(By.xpath("//div[@class='center-part']//input[@name='q']")).sendKeys("SSD Samsung");
        getDriver().findElement(By.xpath("//div[@class='center-part']//button[@class='search-submit center']")).click();
        currentValueOfCart += getItemPrice(1);

        addItemToCart();
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().navigate().back();
        currentValueOfCart += getItemPrice(2);

        addItemToCart();
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());
        Thread.sleep(200);

        priceOfCurrentItem = Integer.parseInt(getDriver()
                .findElement(By.xpath("//*[@class='stuff-price__row stuff-price__has-sale']//*[@class='stuff-price__digits scada']"))
                .getText());

        getDriver().findElement(By.xpath("(//button[@class='plusmin plusmin-plus '])[2]")).click();
        Thread.sleep(500);

        currentValueOfCart += priceOfCurrentItem;
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().findElement(By.xpath("(//a[@class='cart-rm js-cart-rm'])[2]")).click();
        getDriver().findElement(By.xpath("(//a[contains(@class, '_remove')])[2]")).click();
        Thread.sleep(500);

        currentValueOfCart -= priceOfCurrentItem * 2;
        expectedValueOfCart.add(currentValueOfCart);
        actualValueOfCart.add(getCartValue());

        getDriver().findElement(By.xpath("//a[contains(@class, 'empty')]")).click();

        for (int i = 0; i < expectedValueOfCart.size(); i++) {
            Assert.assertEquals(actualValueOfCart.get(i), expectedValueOfCart.get(i));
        }
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='not-found__content']"))
                .isDisplayed());
    }

    private void openCart(){
        getDriver().findElement(By.xpath("//ul[contains(@class,'mobile')]//a[contains(@class,'cart')]")).click();
    }
    private int getCartValue(){
        return Integer.parseInt(getDriver()
                .findElement(By.xpath("//div[contains(@class, 'full')]//strong[contains(@class, 'price')]"))
                .getText());
    }

    private void addItem(){
        getDriver().findElement(By.xpath("(//a[@class='add add-cart'])[1]")).click();
    }

    private int getItemPrice(int itemNumber){
        return Integer.parseInt(getDriver()
                .findElement(By.xpath("(//*[@class='stuff-price__digits'])[" + itemNumber + "]"))
                .getText());
    }

    private void pressTwiceEscape(){
        getDriver().findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        getDriver().findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
    }

    private void addItemToCart() throws InterruptedException {
        addItem();
        pressTwiceEscape();
        Thread.sleep(200);
        openCart();
    }
}