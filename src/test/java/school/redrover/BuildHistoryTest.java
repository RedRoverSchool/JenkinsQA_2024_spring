package school.redrover;


import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;


@Epic("Build History")
public class BuildHistoryTest extends BaseTest {
    private static final String PROJECT_NAME = "My freestyle project";

    @Test
    @Story("Start to build a project")
    @Description("Check the project is displayed in the table on the Homepage")
    public void testGetTableBuildHistory() {
        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        List<String> list = new HomePage(getDriver())
                .scheduleBuildForItem(PROJECT_NAME)
                .clickBuildHistory()
                .getBuildsList();

        Assert.assertTrue(list.contains(PROJECT_NAME));
    }

    @Test
    @Story("Start to build a project")
    @Description("Check the project is displayed in the table on the Homepage")
    public void testCheckBuildOnBoard() {
        final String freestyleProjectName = "FREESTYLE";

        TestUtils.createFreestyleProject(this, freestyleProjectName);

        boolean projectNameOnTimeline = new HomePage(getDriver())
                .clickJobByName(freestyleProjectName, new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearience()
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        Assert.assertTrue(projectNameOnTimeline);
    }

    @Test
    @Story("Start to build a project")
    @Description("Check 'Build Scheduled' notification is displayed")
    public void testBuildScheduledMessage() {
        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        String buildScheduledMessageReceived = new HomePage(getDriver())
                .clickGreenBuildArrowButton()
                .getBuildScheduledMessage();

        Assert.assertEquals(buildScheduledMessageReceived, "Build scheduled");
    }
}
