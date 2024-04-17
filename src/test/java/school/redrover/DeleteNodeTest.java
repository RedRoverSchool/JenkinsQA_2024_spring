package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DeleteNodeTest extends BaseTest {

    public void testCreateNode() {

        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestNode");
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate' and @name='Submit']")).click();
    }

    @Test
    public void testDeleteExistingNode() {

        final String expectedResult = "Nodes";

        testCreateNode();

        getDriver().findElement(By.xpath("//a[@href='../computer/TestNode/']")).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Agent']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        final String actualResult = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar']//h1")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
