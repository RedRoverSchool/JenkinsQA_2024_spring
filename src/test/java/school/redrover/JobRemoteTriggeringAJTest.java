package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.JobBuildConsolePage;
import school.redrover.model.UserConfigurePage;
import school.redrover.runner.BaseTest;

public class JobRemoteTriggeringAJTest extends BaseTest {

    @Test
    public void testFreestyleJobRemoteTriggering() {
        final String projectName = "Project1";

        //Precondition
        final String[] tokenUuidUser = new HomePage(getDriver())
                .openUserConfigurations()
                .getTokenUuidUser(projectName);

        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        new UserConfigurePage(getDriver())
                .clickSaveButton()
                .clickLogo();


        //Test steps
        final String actualConsoleLogs = new HomePage(getDriver())
                .createFreestyleProjectWithConfigurations(projectName)
                .triggerJobViaHTTPRequest(token, user, projectName)
                .clickSuccessConsoleOutputButton()
                .getConsoleLogsText();

        new JobBuildConsolePage(getDriver())
                .revokeTokenViaHTTPRequest(token, uuid, user);

        Assert.assertTrue(
                actualConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely."
        );
        Assert.assertFalse(
                actualConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user."
        );

        final String emptyTokenMessage = new JobBuildConsolePage(getDriver())
                .clickLogo()
                .openUserConfigurations()
                .getTokenMessage();

        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }
}
