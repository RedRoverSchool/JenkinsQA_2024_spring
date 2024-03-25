package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class TestLoginConduit {
    @Test

    public void login() {
        String userName ="Grechka"+ Math.random()*3 ;
        String email = "Suyn@mail" + userName + ".ru";
        String password = "W1234567" + Math.random()*3;


        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://demo.realworld.io/#/register");
        WebElement textBoxUserName = driver.findElement(By.xpath("//input[ @ng-model='$ctrl.formData.username']"));
        textBoxUserName.sendKeys(userName);

        WebElement textBoxEmail = driver.findElement(By.xpath("//input[@ng-model=\"$ctrl.formData.email\"]"));
        textBoxEmail.sendKeys(email);

        WebElement textBoxPassword = driver.findElement(By.xpath("//input[@ng-model=\"$ctrl.formData.password\"]"));
        textBoxPassword.sendKeys(password);

        WebElement buttons = driver.findElement(By.xpath("//button[@ng-bind='$ctrl.title']"));
        buttons.click();
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://demo.realworld.io/#/register";

        Assert.assertEquals(actualUrl, expectedUrl, "URL не совпадает");


        driver.quit();
    }
}
