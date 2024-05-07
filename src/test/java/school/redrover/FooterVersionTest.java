package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FooterVersionTest extends BaseTest {

    @Test
    public void testFooterVersion() {
        final String jenkinsVersion = "Jenkins 2.440.2";

        List<String> versionCheckPages = List.of(
                new CreateNewItemPage(getDriver()).getCreateNewItemPageUrl(),
                new PeoplePage(getDriver()).getPeoplePageUrl(),
                new BuildHistoryPage(getDriver()).getBuildHistoryPageUrl(),
                new ManageJenkinsPage(getDriver()).getManageJenkinsPage(),
                new MyViewsPage(getDriver()).getMyViewsPageUrl());

        for (String versionCheckPage : versionCheckPages) {
            TestUtils.openPageInNewTab(this, versionCheckPage);

            Assert.assertEquals(TestUtils.getFooterVersionText(this), jenkinsVersion);
        }
    }
}
