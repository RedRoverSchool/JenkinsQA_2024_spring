package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NodesTablePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

@Epic("Nodes")
public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";

    @BeforeMethod
    private void beforeMethod() {
        HomePage homePage = new HomePage(getDriver());
        if (homePage.isNodesDisplayedOnExecutorsPanel()) {
            homePage.clickOnExecutorPanelToggle();
        }
    }

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
    @Story("US_15.001 Create Node")
    @Description("Verify a created via home page Node can be seen on home page")
    public void testCreatedNodeIsOnHomePage() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .clickLogo();

        Allure.step("Expected result: Created Node is displayed on Home page");
        Assert.assertTrue(homePage.isNodeDisplayed(NODE_NAME));
        Assert.assertTrue(homePage.getNodesList().contains(NODE_NAME), "The created node name is not " + NODE_NAME);
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Verify a created via home page Node can be seen in Nodes table")
    public void testCreatedNodeIsInNodesTable() {
        createNode(NODE_NAME);

        NodesTablePage nodesTablePage = new NodesTablePage(getDriver());

        Allure.step("Expected result: Created Node is displayed in Nodes table");
        Assert.assertTrue(nodesTablePage.isNodeDisplayedInTable(NODE_NAME));
        Assert.assertTrue(nodesTablePage.getNodesInTableList().contains(NODE_NAME),
                "The created node '" + NODE_NAME + "' is not in the Nodes table");
    }

    @Test
    @Story("US_15.004 Check tooltips on Configure Node Monitors page")
    @Description("Verify all tooltips on Configure Node Monitors page")
    public void testTooltipOnConfigureNodeMonitorsPage() {
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
                .clickConfigureMonitorsButton()
                .getTooltipsOnConfigureNodePage();

        Allure.step("Expected result: The tooltips list matches the given one");
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    @Story("US_15.005 Check list of Monitoring Data")
    @Description("Verify Monitoring Data list of the Built-In Node")
    public void testBuiltInNodeMonitoringDataList() {
        final List<String> expectedMonitoringDataValues = new ArrayList<>(List.of(
                "Architecture",
                "Clock Difference",
                "Free Disk Space",
                "Free Swap Space",
                "Free Temp Space",
                "Response Time"
        ));

        List<String> actualMonitoringDataValues = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNode()
                .clickMonitoringDataButton()
                .getMonitoringDataElementsList();

        Allure.step("Expected result: The actual list of Monitoring Data matches the expected one");
        Assert.assertEquals(actualMonitoringDataValues, expectedMonitoringDataValues);
    }

    @Test
    @Story("US_15.002 Delete Node")
    @Description("Delete Node using dropdown menu and check that this Node not displayed in Nodes table")
    public void testDeleteNodeViaDropdownMenu() {
        createNode(NODE_NAME);

        boolean isNodeExist = new NodesTablePage(getDriver())
                .openDropdownChevron(NODE_NAME)
                .clickDeleteAgentOnDropdownMenu()
                .clickYesInDeleteAgentWindow()
                .isContainNode(NODE_NAME);

        Allure.step("Expected result: The deleted Node is not exist and not displayed");
        Assert.assertFalse(isNodeExist);
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Verify error message when create Node with existing name")
    public void testCreateNodeUsingExistingNameAndVerifyErrorMessage() {
        final String nodeName = "NewNode";
        final String expectedResult = "Agent called ‘" + nodeName + "’ already exists";

        createNode(nodeName);

        String actualResult = new NodesTablePage(getDriver())
                .clickNewNodeButton()
                .typeNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickCreateButtonOnError()
                .getErrorMessageText();

        Allure.step("Expected result: Error message: Agent called ‘" + nodeName + "’ already exists");
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Create Node with one label")
    public void testCreateNodeWithOneLabel() {
        String labelName = "NewLabelName";

        String actualResult = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .typeToLabelsInputField(labelName)
                .clickSaveButton()
                .clickNode(NODE_NAME)
                .getLabels();

        Allure.step("Expected result: Label on Node page - '" + labelName + "'");
        Assert.assertTrue(actualResult.contains(labelName));
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Create Node with description")
    public void testCreateNodeWithDescription() {
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

        Allure.step("Expected result: Description text exists and matches the entered");
        Assert.assertTrue(actualResult.contains(description));
    }

    @Test
    @Story("US_15.003 Edit Node")
    @Description("Switch Node to offline status")
    public void testSwitchNodeToOfflineStatus() {
        final String expectedNodeStatusMessage = "Disconnected by admin";

        String actualNodeStatusMessage = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNode()
                .clickMarkThisNodeTemporaryOfflineButton()
                .clickMarkThisNodeTemporaryOfflineConfirmationButton()
                .getNodeOfflineStatusText();

        Allure.step("Expected Node status message: " + expectedNodeStatusMessage);
        Assert.assertEquals(actualNodeStatusMessage, expectedNodeStatusMessage);
    }

    @Test(dependsOnMethods = "testSwitchNodeToOfflineStatus")
    @Story("US_15.003 Edit Node")
    @Description("Return online status to Node")
    public void testSwitchNodeToOnlineStatus() {
        Boolean isNodeOffline = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNode()
                .clickBringThisNodeBackOnlineButton()
                .isNodeOfflineStatusMessageDisplayed();

        Allure.step("Expected result: Message about Disconnecting is missing");
        Assert.assertFalse(isNodeOffline);
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Create Node using invalid data: unsafe char in name")
    public void testCreateNodeWithInvalidData() {
        String actualResult = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodesLink()
                .clickNewNodeButton()
                .typeNodeName("!")
                .selectPermanentAgentRadioButton()
                .clickCreateButtonOnError()
                .getErrorMessageText();

        Allure.step("Expected result: Error message - ‘!’ is an unsafe character");
        Assert.assertEquals(actualResult, "‘!’ is an unsafe character");
    }

    @Test
    @Story("US_15.001 Create Node")
    @Description("Create Node from Manage Jenkins")
    public void testCreateNodeFromManageJenkins() {
        List<String> nodesList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodesLink()
                .clickNewNodeButton()
                .typeNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickCreateButton()
                .clickSaveButton()
                .getNodesInTableList();

        Allure.step("Expected result: The Name of created Node is displayed in Nodes table.");
        Assert.assertTrue(nodesList.contains(NODE_NAME));
    }

    @Test
    @Story("US_15.002 Delete Node")
    @Description("Delete Node using sidebar menu and check that using searchbox in Header")
    public void testDeleteNodeViaSidebarMenu() {
        createNode(NODE_NAME);

        String searchResult = new NodesTablePage(getDriver())
                .clickNode(NODE_NAME)
                .clickDeleteAgent()
                .clickYesButton()
                .getHeader().typeSearchQueryAndPressEnter(NODE_NAME)
                .getNoMatchText();

        Allure.step("Expected result: Node '" + NODE_NAME + "' not found");
        Assert.assertEquals(searchResult, "Nothing seems to match.");
    }
}
