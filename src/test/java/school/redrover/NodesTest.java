package school.redrover;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NodesTest extends BaseTest {

    private static final String NODE_NAME = "FirstNode";

    public NodesTablePage createNewNode(String nodeName) {

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .setNodeName(nodeName)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton();
        return new NodesTablePage(getDriver());
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

    @Test
    public void testCreateNewNodeWithDescription() {
        String description = "Description for user in node is correct and useful for next step";

        String actualResult = new HomePage(getDriver())
                .clickNodesLink()
                .clickNewNodeButton()
                .setNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .addDescription(description)
                .clickSaveButton()
                .clickNode(NODE_NAME)
                .getDescription();

        Assert.assertTrue(actualResult.contains(description));
    }

    @Test
    public void testNumberOfItems() {

        HomePage homePage = new HomePage(getDriver());
        String text = homePage.getBuildExecutorStatusText();
        List<WebElement> buildExecutors = homePage.getBuildExecutorStatusList();
        int number = homePage.getBuildExecutorListSize();

        if(text.contains("( offline)")) {
            int number1 = buildExecutors.size();

            Assert.assertEquals(number, number1);

        } else if(number >= 1){
            int number2 = buildExecutors.size();

            Assert.assertEquals(number, number2);
        }
    }

    @Test
    public void testSwitchNodeToOfflineStatus() {
        final String nodeStatusMessage = "Disconnected by admin";

        String nodeStatus = new HomePage(getDriver())
                .clickNodesLink()
                .clickOnBuiltInNode()
                .clickMarkThisNodeTemporaryOfflineButton()
                .clickMarkThisNodeTemporaryOfflineConfirmationBtn()
                .getNodeOnlineStatusText();

        Assert.assertTrue(nodeStatus.contains(nodeStatusMessage));
    }

    @Test(dependsOnMethods = "testSwitchNodeToOfflineStatus")
    public void testSwitchNodeToOnlineStatus() {

         NodeManagePage nodeStatus = new HomePage(getDriver())
                .clickNodesLink()
                .clickOnBuiltInNode()
                .clickBringThisNodeBackOnlineBtn();

        Assert.assertTrue(nodeStatus.nodeOnlineStatusText().isEmpty());
    }

    @Test
    public void testCreateNewNodeWithInvalidData() {

        String actualResult = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .setNodeName("!")
                .selectPermanentAgentRadioButton()
                .clickOkButtonOnError()
                .getErrorMessageText();

        Assert.assertEquals(actualResult, "‘!’ is an unsafe character");
    }

    @Test
    public void testCreateNodeFromManageJenkins() {

        List<String> nodesList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickNewNodeButton()
                .setNodeName(NODE_NAME)
                .selectPermanentAgentRadioButton()
                .clickOkButton()
                .clickSaveButton()
                .getNodesinTableList();

        Assert.assertTrue(nodesList.contains(NODE_NAME));
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

    @Test
    public void testDeleteExistingNode() {

        createNewNode(NODE_NAME)
                .clickNode(NODE_NAME)
                .clickDeleteAgent()
                .clickYesButton();

        String searchResult = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(NODE_NAME)
                .getNoMatchText();

        Assert.assertEquals(searchResult, "Nothing seems to match.");
    }
}