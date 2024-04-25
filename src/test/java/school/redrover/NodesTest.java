package school.redrover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";
    public static final By NODE_TABLE_LOCATOR = By.cssSelector(
        "a[href='../computer/" + NODE_NAME + "/']");

    private void createNodeViaMainPage() {
        getDriver().findElement(By.cssSelector("[href='/computer/']")).click();
        getDriver().findElement(By.cssSelector("[href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector("[class$=radio__label]")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void deleteNodeViaNodesTable(By nodeTableLocator) {
        WebElement createdNode = getDriver().findElement(nodeTableLocator);
        new Actions(getDriver()).moveToElement(createdNode).perform();
        WebElement dropdownChevron =getDriver().findElement(By.cssSelector("#node_" + NODE_NAME + " > td:nth-child(2) > a > button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
            "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getDriver().findElement(By.cssSelector("button[href$='doDelete']")).click();
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();
    }

    @Test
    public void testAddNode() {
        String text;

        getDriver().findElement(By.xpath("//*[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//*[@href='computer']")).click();
        getDriver().findElement(By.xpath("//*[@href='new']")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text, "New node");
    }

    @Test
    public void testBuiltInNodeMonitoringDataList() {
        final List<String> expectedMonitoringDataValues = new ArrayList<>(List.of("Architecture", "Response Time",
                "Clock Difference", "Free Temp Space", "Free Disk Space", "Free Swap Space"));

        getDriver().findElement(By.cssSelector("[href='/computer/']")).click();
        getDriver().findElement(By.cssSelector("[href*='built-in']")).click();
        getDriver().findElement(By.className("advancedButton")).click();

        List<WebElement> monitoringDataElements = getDriver()
                .findElements(By.cssSelector("[class*='jenkins-table'] td:nth-of-type(odd)"));
        List<String> actualMonitoringDataValues = new ArrayList<>();
        for (WebElement element : monitoringDataElements) {
            actualMonitoringDataValues.add(element.getText());
        }

        try {
            Assert.assertEquals(actualMonitoringDataValues, expectedMonitoringDataValues,
                    "Actual Monitoring Data list is different");
        } catch (AssertionError e) {
            Collections.sort(expectedMonitoringDataValues);
            Assert.assertEquals(actualMonitoringDataValues, expectedMonitoringDataValues,
                    "Actual Monitoring Data list is different after sorting expected values alphabetically");
        }
    }

    @Test
    public void testCreatedNodeIsOnMainPage() {
        createNodeViaMainPage();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        WebElement createdNode = getDriver().findElement(By.cssSelector("[href='/computer/" + NODE_NAME + "/']"));

        Assert.assertTrue(createdNode.isDisplayed());
        Assert.assertEquals(createdNode.getText(), NODE_NAME, "The created node name is not " + NODE_NAME);
    }

    @Test
    public void testCreatedNodeIsInNodesTable() {
        createNodeViaMainPage();
        WebElement createdNodeInNodesTable = getDriver().findElement(By.cssSelector("[href='../computer/" + NODE_NAME + "/']"));

        Assert.assertTrue(createdNodeInNodesTable.isDisplayed());
        Assert.assertEquals(createdNodeInNodesTable.getText(), NODE_NAME,
                "The created node '" + NODE_NAME + "' is not in the Nodes table");
    }

    @Test
    public void testDeletedNodeNotDisplayedInNodesTable() {
        createNodeViaMainPage();
        deleteNodeViaNodesTable(NODE_TABLE_LOCATOR);
        
        Assert.assertTrue(getDriver().findElements(NODE_TABLE_LOCATOR).isEmpty());
    }
}
