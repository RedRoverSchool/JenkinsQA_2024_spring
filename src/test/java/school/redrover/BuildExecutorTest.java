package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class BuildExecutorTest extends BaseTest {
    
    @Test
    public void testChangeNumberOfExecutors() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        getDriver().findElement(By.xpath("//input[@name='_.numExecutors']")).clear();
        getDriver().findElement(By.xpath("//input[@name='_.numExecutors']")).sendKeys("3");
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<WebElement> buildExecutors = getDriver().findElements(By.xpath("//td[text()='Idle']"));

        Assert.assertEquals(buildExecutors.size(), 3);
    }
}

