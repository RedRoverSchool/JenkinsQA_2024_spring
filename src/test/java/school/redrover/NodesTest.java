package school.redrover;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NodeBuiltInStatusPage;
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
    public void testTooltipConfigureNodePage() {
        List<String> expectedList = List.of(
                "Help for feature: Architecture",
                "Help for feature: Clock Difference",
                "Help for feature: Free Disk Space",
                "Help for feature: Don&#039;t mark agents temporarily offline",
                "Help for feature: Free Space Threshold",
                "Help for feature: Free Space Warning Threshold",
                "Help for feature: Free Swap Space",
                "Help for feature: Free Temp Space",
                "Help for feature: Don&#039;t mark agents temporarily offline",
                "Help for feature: Free Space Threshold",
                "Help for feature: Free Space Warning Threshold",
                "Help for feature: Response Time",
                "Help for feature: Don&#039;t mark agents temporarily offline"
        );
        List<String> actualList = new HomePage(getDriver())
                .clickNodesLink()
                .clickConfigureMonitorButton()
                .getTooltipsConfigureNodePage();

        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void testBuiltInNodeMonitoringDataList() {
        final List<String> expectedMonitoringDataValues = new ArrayList<>(List.of("Architecture", "Response Time",
                "Clock Difference", "Free Temp Space", "Free Disk Space", "Free Swap Space"));

        List<String> actualMonitoringDataValues = new HomePage(getDriver())
                .clickNodesLink()
                .clickBuiltInNodeName()
                .clickMonitoringDataButton()
                .getMonitoringDataElementsList();

        new NodeBuiltInStatusPage(getDriver()).
                assertMonitoringDataValues(actualMonitoringDataValues, expectedMonitoringDataValues);
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

    @Test
    public void testVerifyErrorMessage() {

        final String expectedResult = "Agent called ‘NewNode’ already exists";
        final String nodeName = "NewNode";

        String actualResult = new HomePage(getDriver())
                .clickNodesLink()
                .clickNewNodeButton()
                .setNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton()
                .clickNewNodeButton()
                .setNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickOkButtonOnError()
                .getErrorMessageText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCreateNewNodeWithOneLabel() {
        String labelName = "NewLabelName";

        String actualResult = new HomePage(getDriver())
                .clickNodesLink()
                .clickNewNodeButton()
                .setNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .createLabel(labelName)
                .clickSaveButton()
                .clickNode(NODE_NAME)
                .getLabels();

        Assert.assertTrue(actualResult.contains(labelName));
    }
}
