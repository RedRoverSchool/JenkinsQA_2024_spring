package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class SileniumTest extends BaseTest {
    @Test
    public void testFirst() {
        getDriver().get("https://www.selenium.dev/");
        WebElement title = getDriver().findElement(By.xpath("//h4[@class='h3 mb-3 selenium-webdriver']"));

        Assert.assertEquals(title.getText(), "Selenium WebDriver");
    }
    @Test
    public void testButton(){
        getDriver().get("https://www.selenium.dev/");

        WebElement dropButtonAbout= getDriver().findElement(By.xpath("//a[@id='navbarDropdown']"));
        dropButtonAbout.click();

        List<WebElement> dropdownItems = getDriver().findElements(By.xpath("//div[@class='dropdown-menu show']/a"));
        int expectedItemCount = 8;

        Assert.assertEquals(expectedItemCount, dropdownItems.size());
    }
    @Test
    public void testList() {
        getDriver().get("https://www.selenium.dev/");

        WebElement dropButtonAbout = getDriver().findElement(By.xpath("//a[@id='navbarDropdown']"));
        dropButtonAbout.click();

        List<WebElement> dropdownItems = getDriver().findElements(By.xpath("//div[@class='dropdown-menu show']/a"));
        String item1 = dropdownItems.get(0).getText();
        System.out.println(item1);

        Assert.assertEquals(item1, "About Selenium");
    }
    }


