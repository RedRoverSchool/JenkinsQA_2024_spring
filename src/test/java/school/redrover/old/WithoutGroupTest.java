package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

@Ignore
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

    @Test
    public void testTextBox() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.cssSelector(".card:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".btn-light:nth-child(1)")).click();

        driver.findElement(By.id("userName")).sendKeys("Lilia");
        driver.findElement(By.id("userEmail")).sendKeys("test@gmail.com");

        WebElement footer = driver.findElement(By.tagName("footer"));
        int deltaY = footer.getRect().y;
        new Actions(driver)
                .scrollByAmount(0, deltaY)
                .perform();

        Thread.sleep(1000);

        driver.findElement(By.className("text-right")).click();

        Assert.assertEquals(driver.findElement(By.id("name")).getText(), "Name:Lilia");
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), "Email:test@gmail.com");
    }

    @Test
    public void TestAliceAndCat() {
        getDriver().get("https://aliceandcat.ru/");
        WebElement harryPotterButton = getDriver().findElement(By.id("menu-item-1590"));
        harryPotterButton.click();
        WebElement wizardingBooksBuy = getDriver().findElement(By.xpath("//*[@data-productid='1460']"));
        wizardingBooksBuy.click();
        WebElement checkoutForm = getDriver().findElement(By.className("woocommerce"));
        Assert.assertNotNull(checkoutForm, "Element Checkout Form is not found");
    }
}
