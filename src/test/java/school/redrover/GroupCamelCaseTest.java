package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupCamelCaseTest extends BaseTest {

    @Test
    public void testOnliner() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.onliner.by/");

        WebElement linkSmart = driver.findElement(By.className("project-navigation__sign"));
        linkSmart.click();

        Thread.sleep(1000);

        WebElement linkHonor = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/div/div[3]/div/div[3]/div[2]/div/div[1]/div[1]/div/div[1]/a"));
        linkHonor.click();

        Thread.sleep(1000);

        WebElement buttonOffer = driver.findElement(By.cssSelector("a.button:nth-child(1) > span:nth-child(1)"));
        buttonOffer.click();

        Thread.sleep(1000);

        WebElement buttonFilterDropdown = driver.findElement(By.className("input-style__real"));
        buttonFilterDropdown.click();

        Thread.sleep(1000);

        WebElement textFilterDropdown = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[2]/div/div[2]/div/div/select/option[2]"));
        textFilterDropdown.click();

        Thread.sleep(2000);

        WebElement buttonPay = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[4]/div[1]/div/div/div[2]/div[1]/a[1]"));
        buttonPay.click();

        Thread.sleep(5000);

        WebElement message = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div[2]/div/div[1]"));
        String value = message.getText();

        Thread.sleep(1000);

        Assert.assertEquals("Оформление заказа", value);
    }
}
