package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes2Test extends BaseTest {
    @Test
    public void testVerifyErrorMessageWhenCreatingNodeWithExistingNameInJenkins() throws InterruptedException {
        String nodeName = "NewNode";
        final String expectedResult = "Agent called ‘NewNode’ already exists";

        getDriver().findElement(By.xpath("//*[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text() ='Nodes']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.xpath("//input[@ id='name']")).sendKeys(nodeName);
        getDriver().findElement
                (By.xpath("//label[@for='hudson.slaves.DumbSlave' and contains(@class, 'jenkins-radio__label')]")).click();
        getDriver().findElement
                (By.xpath("//button[@id='ok' and contains(@class, 'jenkins-button--primary')]")).click();
        getDriver().findElement
                (By.xpath("//button[normalize-space(text())='Save']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.xpath("//input[@ id='name']")).sendKeys(nodeName);
        getDriver().findElement
                (By.xpath("//label[@for='hudson.slaves.DumbSlave' and contains(@class, 'jenkins-radio__label')]")).click();

        Thread.sleep(500);

        String actualResult = getDriver().findElement(By.xpath("//div[@class='error']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}


