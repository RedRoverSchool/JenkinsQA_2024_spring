package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplitoolDemoTest {

    @Test
    public void testApplitoolsDemo() {

        // A new instance of a web driver interface created for Chrome browser
        WebDriver appliToolsDemo = new ChromeDriver();
        //pass on the target URL
        appliToolsDemo.get("https://demo.applitools.com/");

        //find username field and send over an input
        WebElement userNameField = appliToolsDemo.findElement(By.id("username"));
        userNameField.sendKeys("standard_user");

        // find password field and send over an input
        WebElement passwordField = appliToolsDemo.findElement(By.id("password"));
        passwordField.sendKeys("12345qwera");

        // find log-in button and do the click
        appliToolsDemo.findElement(By.id("log-in")).click();

        //find the static text visible after the user is logged in
        WebElement h6FindText = appliToolsDemo.findElement(By.xpath(("(//h6[@class='element-header'])[1]")));
        String h6String = h6FindText.getText();

        //check if the "Financial Overview" text exists
        Assert.assertEquals(h6String, "Financial Overview");

        //close the browser instance
        appliToolsDemo.quit();


    }
}
