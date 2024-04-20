package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes2Test extends BaseTest {
    public final String NODE_NAME = "New node";

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

    @Test
    public void testCreateNewNodeWithDescription() {
        String description = "Description for user in node is correct and useful for next step";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Nodes']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.name("nodeDescription")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='../computer/" +
                NODE_NAME.replaceAll(" ", "%20") + "/']")).click();

        String actualResult = getDriver().findElement(By.id("description")).getText();

        Assert.assertTrue(actualResult.contains(description));
    }

    @Test
    public void testCreateNewNodeWithOneLabel() {
        String labelName = "NewLabelName";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Nodes']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.name("_.labelString")).sendKeys(labelName);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='../computer/" +
                NODE_NAME.replaceAll(" ", "%20") + "/']")).click();

        String actualResult = getDriver().findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-3']")).getText();

        Assert.assertTrue(actualResult.contains(labelName));
    }
}


