package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Nodes2Test extends BaseTest {
    public final String NODE_NAME = "New node";

    public void createNewNode(String nodeName) {

        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testVerifyErrorMessage() {

        final String expectedResult = "Agent called ‘NewNode’ already exists";
        final String nodeName = "NewNode";

        createNewNode(nodeName);

        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.name("Submit")).click();
        String actualResult = getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();

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

    @Test
    public void testCreateNewNodeWithName() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text()='Nodes']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[text()='Permanent Agent']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();

        String actualResult = getDriver().findElement(By.xpath("//a[@href='../computer/" + NODE_NAME.replaceAll(" ", "%20") + "/']")).getText();

        Assert.assertTrue(actualResult.contains(NODE_NAME));
    }

    @Test
    public void testDeleteNode() {
        final String nodeName = "NewNode";
        createNewNode(nodeName);

        WebElement createdNode = getDriver().findElement(
                By.cssSelector("a[href*='../computer/" + nodeName + "/']"));

        TestUtils.openElementDropdown(this, createdNode);
        WebElement deleteButton = getDriver().findElement(By.xpath("//button[@href='/manage/computer/" + nodeName + "/doDelete']"));
        deleteButton.click();

        WebElement confirmButton = getDriver().findElement(By.cssSelector("[data-id='ok']"));
        confirmButton.click();

        boolean result;

        try {
            getDriver().findElement(By.xpath("//a[contains(@href,'../computer/" + nodeName + "/')]")).isDisplayed();
            result = false;
        } catch (Exception e) {
            result = true;
        }

        Assert.assertTrue(result);
    }
}




