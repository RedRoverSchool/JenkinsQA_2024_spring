package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.concurrent.TimeUnit;

public class GroupForwardTest extends BaseTest {

    @Test
    public void testCostco() {

        getDriver().get("https://www.costco.com/");
        getDriver().findElement(By.xpath("//*[@id='search-field']")).sendKeys("apple");
        getDriver().findElement(By.xpath("//*[@id='search-field']")).sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='crumbs_ul']/li[2]/span")).getText(), "Apple Products");
    }

    @Test
    public void testCostcoReturnToHomePage(){

        getDriver().get("https://www.costco.com/apple-brand-showcase.html");
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().findElement(By.xpath("//*[@id='crumbs_ul']/li[1]/a/span")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.costco.com/");
    }
//вопрос по тесту
    @Ignore  @Test
    public void testdemoQAElements() {

       getDriver().get("https://demoqa.com/");
       getDriver().findElement(By.xpath("//h5[contains(text(), 'Elements')]")).click();
       getDriver().findElement(By.xpath("//span[text() ='Text Box']")).click();
       getDriver().findElement(By.id("userName")).sendKeys("Bob Forest");
       getDriver().findElement(By.id("userEmail")).sendKeys("a@example.com");
       getDriver().findElement(By.id("currentAddress")).sendKeys("8410 N. Rafael Rivera Way, Las Vegas, NV 89113");
       getDriver().findElement(By.id("permanentAddress")).sendKeys("11011 W Charleston Blvd, Las Vegas, NV 89135");
       getDriver().findElement(By.cssSelector("button#submit"));

        Assert.assertTrue(getDriver().findElement(By.id("output")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//p[@id='name']/text()['Bob Forest'][1]")),"Bob Forest");
    }

    @Test
    public void testVenetianrearaurantsHappyHour() {

        getDriver().get("https://www.venetianlasvegas.com/");
        getDriver().findElement(By.xpath("//a[@data-navigation-title='Restaurants']")).click();
        getDriver().findElement(By.xpath("//a [contains(text(), 'Happy')]")).click();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String text = getDriver().findElement(By.xpath("//div[@class='mod-restaurant-browse-hero']//h1")).getText();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='mod-restaurant-browse-hero']")).isDisplayed());
        Assert.assertEquals(text, "Happy Hour");
    }
}

