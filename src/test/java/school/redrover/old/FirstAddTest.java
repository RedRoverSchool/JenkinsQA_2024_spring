package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class FirstAddTest extends BaseTest {

    @Test

    public void testComputers (){
        WebDriver driver =getDriver();
        driver.get("https://computer-database.gatling.io/computers");

        WebElement addButton = driver.findElement(By.id("add"));
        addButton.click();
        WebElement computerName = driver.findElement(By.id("name"));
        computerName.sendKeys("macbook");
        WebElement Introduced = driver.findElement(By.id("introduced"));
        Introduced.sendKeys("2022-01-23");
        WebElement  companyName = driver.findElement(By.id("company"));
        companyName.click();
        WebElement  choice = driver.findElement(By.className("blank"));
        choice.isSelected();
        WebElement submitButton = driver.findElement(By.cssSelector("#main > form > div > input"));
        submitButton.click();

        WebElement alert  = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]"));
        alert.isDisplayed();
        String message = alert.getText();
        Assert.assertEquals(message,"Done ! Computer macbook has been created");

    }

}
