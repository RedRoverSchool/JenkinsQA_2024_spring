package school.redrover;

import school.redrover.runner.BaseTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Nodes2Test extends BaseTest {
    public final String NODE_NAME = "New node";

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
        getDriver().findElement(By.xpath("//a[@href='../computer/"+
                        NODE_NAME.replaceAll(" ", "%20")+"/']")).click();

        String actualResult = getDriver().findElement(By.id("description")).getText();

        Assert.assertTrue(actualResult.contains(description));
    }
}
