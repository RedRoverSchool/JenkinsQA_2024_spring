package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class BuildHistoryTest extends BaseTest{
    private final String PROJECT_NAME = "My freestyle project";

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