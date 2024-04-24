package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject13Test extends BaseTest {


    @Test
    private void testNewFreestyleProjectCreated()  {
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Freestyle1");
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        WebElement okButton = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("ok-button")));
        okButton.click();
        WebElement saveButton = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        saveButton.click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(@class, 'job-index-headline page-headline')]")).getText(),
                "Freestyle1");

    }
}
