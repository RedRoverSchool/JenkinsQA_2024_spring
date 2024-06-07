package school.redrover;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

@Epic("Nodes")
public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";

    @Step("Create Node")
    public void createNode(String nodeName) {

        new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton();
    }

    @Test
    @Story("US_15.001 Create new node")
    @Description("Check number of color themes that are available")
    public void testCreatedNodeIsOnHomePage() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .clickLogo();

        Assert.assertTrue(homePage.isNodeDisplayed(NODE_NAME));
        Assert.assertTrue(homePage.getNodesList().contains(NODE_NAME), "The created node name is not " + NODE_NAME);
    }

    @Test
    public void testCreatedNodeIsInNodesTable() {
        NodesTablePage nodesTablePage = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton();

        Assert.assertTrue(nodesTablePage.isNodeDisplayedInTable(NODE_NAME));
        Assert.assertTrue(nodesTablePage.getNodesinTableList().contains(NODE_NAME),
                "The created node '" + NODE_NAME + "' is not in the Nodes table");
    }

    @Test
    public void testTooltipOnConfigureNodePage() {
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
                .clickBuildExecutorStatusLink()
                .clickConfigureMonitorButton()
                .getTooltipsOnConfigureNodePage();

        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void testBuiltInNodeMonitoringDataList() {
        final List<String> expectedMonitoringDataValues = new ArrayList<>(List.of(
                "Architecture",
                "Response Time",
                "Clock Difference",
                "Free Temp Space",
                "Free Disk Space",
                "Free Swap Space"));

        List<String> actualMonitoringDataValues = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNodeName()
                .clickMonitoringDataButton()
                .getMonitoringDataElementsList();

        TestUtils.assertEqualsLists(actualMonitoringDataValues, expectedMonitoringDataValues);
    }

    @Test
    public void testDeletedNodeNotDisplayedInNodesTable() {

        createNode(NODE_NAME);

        boolean isNodeExist = new NodesTablePage(getDriver())
                .openDropDownChevron(NODE_NAME)
                .deleteNodeViaOpenedDropDownChevron()
                .isContainNode(NODE_NAME);

        Assert.assertFalse(isNodeExist);
    }

    @Test
    public void testVerifyErrorMessage() {

        final String expectedResult = "Agent called ‘NewNode’ already exists";
        final String nodeName = "NewNode";

        String actualResult = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .clickNewNodeButton()
                .typeNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickCreateButtonOnError()
                .getErrorMessageText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCreateNodeWithOneLabel() {
        String labelName = "NewLabelName";

        String actualResult = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .typeToLabelsField(labelName)
                .clickSaveButton()
                .clickNode(NODE_NAME)
                .getLabels();

        Assert.assertTrue(actualResult.contains(labelName));
    }

    @Test
    public void testCreateNewNodeWithDescription() {
        String description = "Description for user in node is correct and useful for next step";

        String actualResult = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .typeDescriptionText(description)
                .clickSaveButton()
                .clickNode(NODE_NAME)
                .getDescription();

        Assert.assertTrue(actualResult.contains(description));
    }

    @Test
    public void testSwitchNodeToOfflineStatus() {
        final String expectedNodeStatusMessage = "Disconnected by admin";

        String actualNodeStatusMessage = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickOnBuiltInNode()
                .clickMarkThisNodeTemporaryOfflineButton()
                .clickMarkThisNodeTemporaryOfflineConfirmationButton()
                .getNodeOfflineStatusText();

        Assert.assertEquals(actualNodeStatusMessage, expectedNodeStatusMessage);
    }

    @Test(dependsOnMethods = "testSwitchNodeToOfflineStatus")
    public void testSwitchNodeToOnlineStatus() {

        Boolean isNodeOffline = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickOnBuiltInNode()
                .clickBringThisNodeBackOnlineButton()
                .isNodeOfflineStatusMessageDisplayed();

        Assert.assertFalse(isNodeOffline);
    }

    @Test
    public void testCreateNewNodeWithInvalidData() {

        String actualResult = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .typeNodeName("!")
                .selectPermanentAgentRadioButton()
                .clickCreateButtonOnError()
                .getErrorMessageText();

        Assert.assertEquals(actualResult, "‘!’ is an unsafe character");
    }

    @Test
    public void testCreateNodeFromManageJenkins() {

        List<String> nodesList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .getNodesinTableList();

        Assert.assertTrue(nodesList.contains(NODE_NAME));
    }

    @Test
    public void testDeleteExistingNode() {

        createNode(NODE_NAME);

        String searchResult = new NodesTablePage(getDriver())
                .clickNode(NODE_NAME)
                .clickDeleteAgent()
                .clickYesButton()
                .getHeader().typeSearchQueryPressEnter(NODE_NAME)
                .getNoMatchText();

        Assert.assertEquals(searchResult, "Nothing seems to match.");
    }
}