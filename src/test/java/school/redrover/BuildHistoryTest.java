package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import school.redrover.model.BuildHistoryPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.AssertUtils;
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
                .clickScheduleBuildForItemAndWaitForBuildSchedulePopUp(PROJECT_NAME)
                .clickBuildHistory()
                .getBuildsList();

        AssertUtils
                .allureAnnotation("List contains project name in the table on the Homepage")
                .listContainsObject(list, PROJECT_NAME, "Project name not found in list!");
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
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        AssertUtils
                .allureAnnotation("Project name appears in the table on the BuildHistory Page")
                .isTrue(isProjectNameOnTimeline);
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

        AssertUtils
                .allureAnnotation("'Build scheduled' message should appear after clicking green triangle button")
                .equals(buildScheduledMessageReceived, buildScheduledMessageActual);
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

        AssertUtils
                .allureAnnotation("message should appear after clicking 'Build Now' from dropdown menu")
                .equals(buildDoneGreenMessageActual, buildDoneGreenMessageExpected);
    }

    @Test(dependsOnMethods = "testGetTableBuildHistory")
    @Story("US_08.002 Get the information about a project build")
    @Description("Check 'Build now: Done.' notification is displayed")
    public void testPermalinksDisplayed() {
        List<String> permalinksExpected =
                List.of("Last build (#1)",
                        "Last stable build (#1)",
                        "Last successful build (#1)",
                        "Last completed build (#1)");

        List<String> permalinksActual = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, new BuildHistoryPage(getDriver()))
                .getPermalinkList();

        AssertUtils
                .allureAnnotation("Permalinks should be displayed on the BuildHistory Page")
                .equals(permalinksActual, permalinksExpected);
    }
}
