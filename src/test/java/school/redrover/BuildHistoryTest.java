package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class BuildHistoryTest extends BaseTest {
    private final String PROJECT_NAME = "My freestyle project";

    @Test
    public void testGetTableBuildHistory() {
        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        List<String> list = new HomePage(getDriver())
                .scheduleBuildForItem(PROJECT_NAME)
                .clickBuildHistory()
                .getBuildsList();

        Assert.assertTrue(list.contains(PROJECT_NAME));
    }

    @Test
    public void testCheckBuildOnBoard() {
        String FREESTYLE_PROJECT_NAME = "FREESTYLE";

        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        boolean projectNameOnTimeline = new HomePage(getDriver())
                .clickJobByName("FREESTYLE", new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearience()
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        Assert.assertTrue(projectNameOnTimeline, "FREESTYLE is display");
    }
}