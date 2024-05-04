package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.UserConfigurePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class JobRemoteTriggeringAJTest extends BaseTest {

    private void revokeTokenViaHTTPRequest(String token, String uuid, String user) {
        final String postRevokeToken = "http://" + user + ":" + token + "@localhost:8080/user/" + user
                + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    @Test
    public void testFreestyleJobRemoteTriggering() {
        final String projectName = "Project1";

        UserConfigurePage userConfigurePage = new HomePage(getDriver()).openUserConfigurations();

        final String[] tokenUuidUser = userConfigurePage.getTokenUuidUser(projectName);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        userConfigurePage
                .clickSaveButton()
                .clickLogo()
                .createFreestyleProjectWithConfigurations(projectName)
                .triggerJobViaHTTPRequest(token, user, projectName);

        int count = 0;
        while(getDriver().findElements(By.xpath("//a[@tooltip='Success > Console Output']")).isEmpty()
                && count < 2){
            getWait60().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")));
            count++;
        }
        getWait60().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']"))).click();

        final String actualConsoleLogs = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("console-output")))
                .getText();

        revokeTokenViaHTTPRequest(token, uuid, user);

        Assert.assertTrue(
                actualConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely."
        );
        Assert.assertFalse(
                actualConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user."
        );

        openUserConfigurations();
        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();

        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }
}
