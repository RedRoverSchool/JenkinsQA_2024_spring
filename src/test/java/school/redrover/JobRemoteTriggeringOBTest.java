package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class JobRemoteTriggeringOBTest extends BaseTest {

    private void openUserConfigurations() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("tasks")));
        getDriver().findElement(By.id("tasks")).findElement(By.linkText("People")).click();
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.id("people")))
                .findElement(By.xpath("//a[contains(@href,'/user/')]"))
                .click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("tasks")))
                .findElement(By.linkText("Configure"))
                .click();
    }

    private String[] getTokenUuidUser(String projectName) {
        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[text()='Add new Token']")))
                .click();
        getDriver().findElement(By.name("tokenName")).sendKeys(projectName);
        getDriver().findElement(By.id("api-token-property-token-save")).click();

        final String token = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[@class='new-token-value visible']")))
                .getText();
        final String uuid = getDriver().findElement(By.name("tokenUuid")).getAttribute("value");
        final String user = getDriver().getCurrentUrl().split("/")[4];

        getDriver().findElement(By.name("Submit")).click();

        return new String[]{token, uuid, user};
    }

    private void createFreestyleProjectWithConfigurations(String projectName) {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.textToBe(By.xpath("//h1"), "Welcome to Jenkins!"));
        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.linkText("New Item")))
                .click();
        getWait5().until(ExpectedConditions
                .presenceOfElementLocated(By.name("name")))
                .sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getWait2().until(ExpectedConditions
                .elementToBeClickable(By.id("ok-button")))
                .click();

        scrollToElement(By.id("build-triggers"));
        getDriver().findElement(By.cssSelector("span:has(input[name='pseudoRemoteTrigger'])")).click();
        getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.name("authToken")))
                .sendKeys(projectName);

        scrollToElement(By.id("build-environment"));
        getDriver().findElement(By.cssSelector("span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper'])"))
                .click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void scrollToElement(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void triggerJobViaHTTPRequest(String token, String user, String projectName) {
        final String postBuildToken = "http://" + user + ":" + token + "@localhost:8080/job/Project1/build?token=" + projectName; //???

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildToken);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    private void revokeTokenViaHTTPRequest(String token, String uuid, String user) {
        final String postRevokeToken = "http://" + user + ":" + token + "@localhost:8080/user/" + user
                + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    @Ignore
    @Test
    public void testFreestyleJobRemoteTriggering() {
        final String projectName = "Project1";

        openUserConfigurations();

        final String[] tokenUuidUser = getTokenUuidUser(projectName);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        createFreestyleProjectWithConfigurations(projectName);

        triggerJobViaHTTPRequest(token, user, projectName);

        getWait60().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")))
                .click();

        final String actualConsoleLogs = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("console-output")))
                .getText();

        revokeTokenViaHTTPRequest(token, uuid, user);

        Assert.assertTrue(
                actualConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely.");
        Assert.assertFalse(
                actualConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user.");

        openUserConfigurations();

        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();

        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }
}
