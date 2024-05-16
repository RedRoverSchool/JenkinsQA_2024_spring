package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class BuildHistoryTest extends BaseTest{

        @Test
        public void testGetTableBuildHistory() {
            String actualTableTitle = new HomePage(getDriver())
                    .clickBuildHistory()
                    .getPageHeading();

        Assert.assertEquals(actualTableTitle, "Build History of Jenkins");
    }

    @Test
    public void testCheckBuildOnBoard(){
        String FREESTYLE_PROJECT_NAME = "FREESTYLE";

        boolean projectNameOnTimeline = new HomePage(getDriver())
                .createFreestyleProject(FREESTYLE_PROJECT_NAME)
                .clickJobByName("FREESTYLE",new FreestyleProjectPage(getDriver()))
                .clickBuildNowOnSideBar()
                .waitBuildToFinish()
                .clickLogo()
                .clickBuildHistory()
                .isDisplayedBuildOnTimeline();

        Assert.assertTrue(projectNameOnTimeline,"FREESTYLE is display");
    }
}