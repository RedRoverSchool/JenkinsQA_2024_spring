package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;


public class ItemTest extends BaseTest {

    @Test
    public void testNumberOfItems() {
        WebElement manageJenkins = getDriver().findElement(By.xpath("//a[@href='/manage']"));
        manageJenkins.click();

        WebElement nodesLink = getDriver().findElement(By.xpath("//a[@href='computer']"));
        nodesLink.click();

        getDriver().findElement(By.id("executors"));

        List<WebElement> buildExecutors = getDriver().findElements(By.xpath("//td[text()='Idle']"));
        int number = buildExecutors.size();

        Assert.assertEquals(number, 2);

    }

}



