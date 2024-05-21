package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.JobBuildConsolePage;
import school.redrover.model.UserConfigurePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class JobRemoteTriggeringTest extends BaseTest {

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



    private void openUserConfigurations1() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("tasks")));
        getDriver().findElement(By.id("tasks")).findElement(By.linkText("People")).click();
        getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.id("people")))
                .findElement(By.xpath("//a[contains(@href, '/user/')]"))
                .click();
        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.id("tasks")))
                .findElement(By.linkText("Configure"))
                .click();
    }

    private String[] getTokenUuidUser1(String projectName) {
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

    private void createFreeStyleProjectWithConfigurations1(String projectName) {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.textToBe(By.xpath("//h1"), "Welcome to Jenkins!"));
        getWait5().until(ExpectedConditions.
                        elementToBeClickable(By.linkText("New Item")))
                .click();
        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.name("name")))
                .sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getWait2().until(ExpectedConditions
                        .elementToBeClickable(By.id("ok-button")))
                .click();

        scrollToElement1(By.id("build-triggers"));
        getDriver().findElement(By.cssSelector("span:has(input[name='pseudoRemoteTrigger'])")).click();
        getWait2().until(ExpectedConditions
                        .visibilityOfElementLocated(By.name("authToken")))
                .sendKeys(projectName);

        scrollToElement1(By.id("build-environment"));
        getDriver().findElement(By.cssSelector("span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper'])"))
                .click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void scrollToElement1(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void triggerJobViaHTTPRequest1(String token, String user, String projectName) {
        final String postBuildToken = "http://" + user + ":" + token + "@localhost:8080/job/Project1/build?token=" + projectName; //???

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildToken);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }


    private void revokeTokenViaHTTPRequest1(String token, String uuid, String user) {
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
    public void testFreestyleJobRemoteTriggering1() {
        final String projectName1 = "Project1";

        openUserConfigurations1();

        final String[] tokenUuidUser = getTokenUuidUser1(projectName1);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        createFreeStyleProjectWithConfigurations1(projectName1);

        triggerJobViaHTTPRequest(token, user, projectName1);

        getWait60().until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")))
                .click();

        final String actialConsoleLogs = getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("console-output")))
                .getText();


        revokeTokenViaHTTPRequest1(token, uuid, user);

        Assert.assertTrue(
                actialConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely.");
        Assert.assertFalse(
                actialConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user.");

        openUserConfigurations1();

        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();

        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }




    private void openUserConfiguration2() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("tasks")));
        getDriver().findElement(By.id("tasks")).findElement(By.linkText("People")).click();
        getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.id("people")))
                .findElement(By.xpath("//a[contains(@href, '/user/')]"))
                .click();
        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.id("tasks")))
                .findElement(By.linkText("Configure"))
                .click();
    }

    private String[] getTokenUuidUser2(String projectName) {
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

    private void createFreestyleProjectWithConfiguration2(String projectName) {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.textToBe(By.xpath("//h1"), "Welcome to Jenkins!"));
        getWait5().until(ExpectedConditions.elementToBeClickable(By
                        .linkText("New Item")))
                .click();
        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.name("name")))
                .sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getWait2().until(ExpectedConditions
                        .elementToBeClickable(By.id("ok-button")))
                .click();

        scrollToElement2(By.id("build-triggers"));
        getDriver().findElement(By.cssSelector("span:has(input[name='pseudoRemoteTrigger'])")).click();
        getWait2().until(ExpectedConditions
                        .visibilityOfElementLocated(By.name("authToken")))
                .sendKeys(projectName);
        scrollToElement2(By.id("build-environment"));
        getDriver().findElement(By.cssSelector("span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper'])"))
                .click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void scrollToElement2(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
    }

    private void triggerJobViaHTTPRequest(String token, String user, String projectName) {
        final String postBuildJob = "http://" + user + ":" + token + "@localhost:8080/job/Project1/build?token=" + projectName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildJob);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    private void revokeTokenViaHTTPRequest2(String token, String uuid, String user) {
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
    public void testFreestyleJobRemoteTriggering2() {
        final String projectName = "Project1";

        openUserConfiguration2();

        final String[] tokenUuidUser = getTokenUuidUser(projectName);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        createFreestyleProjectWithConfiguration2(projectName);

        triggerJobViaHTTPRequest(token, user, projectName);

        getWait60().until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")))
                .click();

        final String actualConsoleLogs = getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("console-output")))
                .getText();

        revokeTokenViaHTTPRequest(token, uuid, user);

        Assert.assertTrue(
                actualConsoleLogs.contains("Started by remote host"),
                "The Build should be triggered remotely.");
        Assert.assertFalse(
                actualConsoleLogs.contains("Started by user"),
                "The Build should NOT be triggered remotely.");

        openUserConfiguration2();
        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();
        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }




    private void openUserConfigurations3() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("tasks")));
        getDriver().findElement(By.linkText("People")).click();
        getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.id("people")))
                .findElement(By.xpath(" //a[contains(@href,'/user/')]"))
                .click();

        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.id("tasks")))
                .findElement(By.linkText("Configure"))
                .click();
    }

    private String[] getTokenUuidUser3(String projectName) {

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
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h1[text()='Welcome to Jenkins!']")));
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

        scrollToElement3(By.id("build-triggers"));
        getDriver().findElement(By.cssSelector("span:has(input[name='pseudoRemoteTrigger'])"))
                .click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("authToken")))
                .sendKeys(projectName);

        scrollToElement(By.id("build-environment"));
        getDriver().findElement(By.cssSelector("span:has(input[name='hudson-plugins-timestamper-TimestamperBuildWrapper']"))
                .click();
        getDriver().findElement(By.name("Submit")).click();

    }

    private void scrollToElement3(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    private void triggerJobHTTPRequest(String token, String user, String projectName) {

        final String postBuildJob = "http://" + user + ":" + token + "@localhost:8080/job/ProjectTest1/build?token=" + projectName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildJob);

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
    public void testFreestyleJobRemoteTriggeringNSTest() {

        final String projectName = "ProjectTest1";

        openUserConfigurations();
        final String[] tokenUuidUser = getTokenUuidUser(projectName);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        createFreestyleProjectWithConfigurations(projectName);

        triggerJobHTTPRequest(token, user, projectName);

        int count = 0;
        while (getDriver().findElements(By.xpath("//a[@tooltip='Success > Console Output']")).isEmpty()
                && count < 2) {
            getWait60().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")));
            count++;
        }
        getWait60().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']"))).click();

        final String actualConsoleLogs = getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("console-output")))
                .getText();

        revokeTokenViaHTTPRequest(token, uuid, user);

        Assert.assertTrue(actualConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely");
        Assert.assertFalse(actualConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user");

        openUserConfigurations();

        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();
        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }



    private void openUserConfigurations() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("tasks")));
        getDriver().findElement(By.id("tasks")).findElement(By.linkText("People")).click();
        getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.id("people")))
                .findElement(By.xpath("//a[contains(@href, '/user/')]"))
                .click();
        getWait5().until(ExpectedConditions
                        .presenceOfElementLocated(By.id("tasks")))
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

    private void createFreeStyleProjectWithConfigurations(String projectName) {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getWait5().until(ExpectedConditions.textToBe(By.xpath("//h1"), "Welcome to Jenkins!"));
        getWait5().until(ExpectedConditions.
                        elementToBeClickable(By.linkText("New Item")))
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
    private void triggerJobViaHTTPRequest(String token, String user, String projectName, String host) {
        final String postBuildToken = "http://" + user + ":" + token + "@" + host + "job/Project1/build?token=" + projectName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildToken);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    private void revokeTokenViaHTTPRequest(String token, String uuid, String user, String host) {
        final String postRevokeToken = "http://" + user + ":" + token + "@" + host + "user/" + user
                + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    @Ignore
    @Test
    public void testFreestyleJobRemoteTriggering4() {
        final String projectName = "Project1";
        final String host = getDriver().getCurrentUrl().substring(7);

        openUserConfigurations();

        final String[] tokenUuidUser = getTokenUuidUser(projectName);
        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        createFreeStyleProjectWithConfigurations(projectName);

        triggerJobViaHTTPRequest(token, user, projectName, host);

        revokeTokenViaHTTPRequest(token, uuid, user, host);

        getWait60().until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//a[@tooltip='Success > Console Output']")))
                .click();

        final String actialConsoleLogs = getWait5().until(ExpectedConditions
                        .visibilityOfElementLocated(By.className("console-output")))
                .getText();

        Assert.assertTrue(
                actialConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely.");
        Assert.assertFalse(
                actialConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user.");

        openUserConfigurations();

        final String emptyTokenMessage = getDriver().findElement(By.cssSelector(".token-list-item>div")).getText();

        Assert.assertEquals(emptyTokenMessage, "There are no registered tokens for this user.");
    }

}

