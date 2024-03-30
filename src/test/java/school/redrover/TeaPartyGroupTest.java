package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeaPartyGroupTest {

    @Test
    public void ivansTest() {

    }


 @Test
    public void natashaTest(){

        WebDriver driver =new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement login = driver.findElement(By.id("user-name"));
        login.sendKeys("standard_user");

        WebElement loginPassword= driver.findElement(By.name("password"));
        loginPassword.sendKeys("secret_sauce");

        WebElement loginButton=driver.findElement(By.id("login-button"));
        loginButton.click();
        driver.quit();



 }

 @Test
    public void natashaFarfetchTest () {

     WebDriver driver = new ChromeDriver();
     driver.get("https://www.farfetch.com/designers/women");

     driver.findElement(By.xpath("//input[@data-testid='search-input']")).sendKeys("Valentino");
     driver.findElement(By.xpath("//input[@data-testid='search-input']")).click();

     driver.get("https://www.farfetch.com/shopping/women/items.aspx");

     driver.findElement(By.xpath("//input[@data-component='SearchInputControlled']")).sendKeys("YSL Heels");
     driver.findElement(By.xpath("//input[@data-testid='search-input']")).click();


     driver.quit();
 }

}
