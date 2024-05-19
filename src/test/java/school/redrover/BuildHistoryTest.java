package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class BuildHistoryTest extends BaseTest {
    private final String PROJECT_NAME = "My freestyle project";

    @Test
    public void testGetTableBuildHistory() {
        List<String> list = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .scheduleBuildForItem(PROJECT_NAME)
                .clickBuildHistory()
                .getBuildsList();

        Assert.assertTrue(list.contains(PROJECT_NAME));
    }

    @Test
    public void testCheckBuildOnBoard() {
        String FREESTYLE_PROJECT_NAME = "FREESTYLE";

        boolean projectNameOnTimeline = new HomePage(getDriver())
                .createFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickJobByName("FREESTYLE", new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitForGreenMarkBuildSuccessAppearience()
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        Assert.assertTrue(projectNameOnTimeline, "FREESTYLE is display");
    }
}