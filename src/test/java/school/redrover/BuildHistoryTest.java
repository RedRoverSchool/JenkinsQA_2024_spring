package school.redrover;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.BuildHistoryPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;


@Epic("Build History")
public class BuildHistoryTest extends BaseTest {
    private static final String PROJECT_NAME = "My freestyle project";

    @Test
    @Story("US_08.002 Get the information about a project build")
    @Description("Check the project is displayed in the table on Homepage")
    public void testGetTableBuildHistory() {
        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        List<String> list = new HomePage(getDriver())
                .scheduleBuildForItem(PROJECT_NAME)
                .clickBuildHistory()
                .getBuildsList();

        Allure.step("Expected result:list contains project name in the table on the Homepage");
        Assert.assertTrue(list.contains(PROJECT_NAME));
    }

    @Test
    @Story("US_08.002 Get the information about a project build")
    @Description("Check the project is displayed in the table on Homepage")
    public void testCheckBuildOnBoard() {
        final String freestyleProjectName = "FREESTYLE";

        TestUtils.createFreestyleProject(this, freestyleProjectName);

        boolean isProjectNameOnTimeline = new HomePage(getDriver())
                .clickJobByName("FREESTYLE", new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearance()
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        Allure.step("Expected result:project name appears in the table on the BuildHistory Page");
        Assert.assertTrue(isProjectNameOnTimeline);
    }

    @Test
    @Story("US_08.001 Start to build a project")
    @Description("Check 'Build Scheduled' notification is displayed")
    public void testBuildScheduledMessage() {
        String buildScheduledMessageActual = "Build scheduled";

        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        String buildScheduledMessageReceived = new HomePage(getDriver())
                .clickGreenBuildArrowButton()
                .getBuildScheduledMessage();

        Allure.step("'Build scheduled' message should appear after clicking green triangle button");
        Assert.assertEquals(buildScheduledMessageReceived, buildScheduledMessageActual);
    }

    @Test
    @Story("US_08.001 Start to build a project")
    @Description("Check 'Build now: Done.' notification is displayed")
    public void testBuildScheduledDoneMessage() {
        String buildDoneGreenMessageExpected = "Build Now: Done.";

        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        String buildDoneGreenMessageActual = new HomePage(getDriver())
                .openItemDropdown(PROJECT_NAME)
                .clickBuildNowFromDropdown()
                .catchBuildNowDoneMessage();

        Allure.step("Expected result: message should appear after clicking 'Build Now' from dropdown menu");
        Assert.assertEquals(buildDoneGreenMessageActual, buildDoneGreenMessageExpected);
    }

    @Test
    @Story("US_08.002 Get the information about a project build")
    @Description("Check 'Build now: Done.' notification is displayed")
    public void testPermalinksDisplayed() {
        List<String> permalinksExpected =
                List.of("Last build (#1)",
                        "Last stable build (#1)",
                        "Last successful build (#1)",
                        "Last completed build (#1)");

        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        List<String> permalinksActual = new HomePage(getDriver())
                .scheduleBuildForItem(PROJECT_NAME)
                .clickJobByName(PROJECT_NAME, new BuildHistoryPage(getDriver()))
                .getPermalinkList();

        Allure.step("Expected result:Permalinks should be displayed on the BuildHistory Page");
        Assert.assertEquals(permalinksActual, permalinksExpected);
    }
}
