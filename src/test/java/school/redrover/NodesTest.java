package school.redrover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NodesTablePage;
import school.redrover.runner.BaseTest;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";

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
        HomePage homePage = new HomePage(getDriver())
                .clickNodesLink()
                .clickNewNodeButton()
                .setNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton()
                .clickLogo();

        Assert.assertTrue(homePage.isNodeDisplayed(NODE_NAME));
        Assert.assertTrue(homePage.getNodesList().contains(NODE_NAME), "The created node name is not " + NODE_NAME);
    }

    @Test
    public void testCreatedNodeIsInNodesTable() {
        NodesTablePage nodesTablePage = new HomePage(getDriver())
                .clickNodesLink()
                .clickNewNodeButton()
                .setNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton();

        Assert.assertTrue(nodesTablePage.isNodeDisplayedInTable(NODE_NAME));
        Assert.assertTrue(nodesTablePage.getNodesinTableList().contains(NODE_NAME),
                "The created node '" + NODE_NAME + "' is not in the Nodes table");
    }

    @Test
    public void testDeletedNodeNotDisplayedInNodesTable() {
        NodesTablePage nodesTablePage = new HomePage(getDriver())
            .clickNodesLink()
            .clickNewNodeButton()
            .setNodeName(NODE_NAME)
            .selectPermanentAgentRadioButton()
            .clickOkButton()
            .clickSaveButton()
            .openDropDownChevron(NODE_NAME)
            .deleteNodeViaOpenedDropDownChevron();

        Assert.assertFalse(nodesTablePage.isConteinNode(NODE_NAME));
    }
}
