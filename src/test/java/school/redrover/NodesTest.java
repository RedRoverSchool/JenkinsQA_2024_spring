package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";

    private void createNodeViaMainPage() {
        getDriver().findElement(By.cssSelector("[href='/computer/']")).click();
        getDriver().findElement(By.cssSelector("[href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NODE_NAME);
        getDriver().findElement(By.cssSelector("[class$=radio__label]")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.name("Submit")).click();
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
}
