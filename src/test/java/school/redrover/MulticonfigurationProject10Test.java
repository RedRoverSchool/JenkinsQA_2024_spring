package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject10Test extends BaseTest {
    @Test
    public void testDescriptionField(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New MultiProject");
        getDriver().findElement(By.xpath("//*[contains(@class,'MatrixProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath
                ("//*[@id='description']/div[1]")).getText().isEmpty());


    }
}

