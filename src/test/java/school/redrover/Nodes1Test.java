package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Nodes1Test extends BaseTest {

    @Test
    public void testCreateNewNode() {

        final String expectedResult = "Node-1";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Node-1");
        getDriver().findElement(By.xpath("//label[@for='hudson.slaves.DumbSlave']")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate' and @name='Submit']")).click();

        String actualResult = getDriver().findElement(By.xpath("//tr[@id='node_Node-1']//a[text()='Node-1']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
