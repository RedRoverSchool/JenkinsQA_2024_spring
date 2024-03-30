package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class W3schoolsTest {

    @Test
    public void w3schoolTest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.w3schools.com/");

        WebElement link = driver.findElement(By.linkText("Java Tutorial")); //<a href="/java/default.asp" class="ga-nav" title="Java Tutorial">JAVA</a>
        link.click();

        WebElement menu = driver.findElement(By.cssSelector("#menubtn_container > span > a")); //бургер-меню
        menu.click();

        WebElement link2 = driver.findElement(By.linkText("Java Variables")); //-XPath: //*[@id="leftmenuinnerinner"]/a[7]
        link2.click();

        WebElement text1 = driver.findElement(By.name("ex1")); // <input name="ex1" maxlength="6" style="width: 65px;">
        text1.sendKeys("String");

        WebElement text2 = driver.findElement(By.name("ex2"));
        text2.sendKeys("carName");

        WebElement text3 = driver.findElement(By.name("ex3"));
        text3.sendKeys("\"Volvo\"");

        WebElement button = driver.findElement(By.cssSelector("#w3-exerciseform > div > button"));
        String resultText = button.getText();
        Assert.assertEquals(resultText, "Submit Answer »");
        button.click();
        driver.quit();
    }
}
