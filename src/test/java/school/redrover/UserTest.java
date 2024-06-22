package school.redrover;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.CreateUserPage;
import school.redrover.model.HomePage;
import school.redrover.model.JobBuildConsolePage;
import school.redrover.model.UserConfigurePage;
import school.redrover.runner.BaseTest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class UserTest extends BaseTest {

    private static final String USER_NAME = "TestUser";
    private static final String PASSWORD = "test123";
    private static final String FULL_NAME = "User";
    private static final String EMAIL_ADDRESS = "test@gmail.com";

    @Test
    public void testCheckUserID() {
        String userID = new HomePage(getDriver())
                .getHeader()
                .clickUserNameOnHeader()
                .getUserID();

        Assert.assertEquals(userID, "Jenkins User ID: admin");
    }

    @Test(dependsOnMethods = "testCheckUserID")
    public void testCreateUserViaManageJenkins() {
        List<String> userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickCreateUser()
                .typeUserName(USER_NAME)
                .setPassword(PASSWORD)
                .setConfirmPassword(PASSWORD)
                .setFullName(FULL_NAME)
                .setEmailAddress(EMAIL_ADDRESS)
                .clickCreateUser()
                .getUsersList();

        Assert.assertTrue(userName.contains("TestUser"));
    }

    @Test(dependsOnMethods = "testCreateUserViaManageJenkins")
    public void testSearchForUserThroughSearchBar() {

        String userFullName = new HomePage(getDriver())
                .getHeader().typeTextToSearchField(FULL_NAME)
                .getHeader().getSearchFieldText();

        Assert.assertEquals(userFullName, "User");
    }

    @Test
    public void testUsersSortingByName() {

        List<String> names = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .clickColumnNameHeader()
                .getUserNames();

        Assert.assertEquals(names, names.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Test
    public void testUsersSortingByUserID() {

        List<String> userIDList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .createUserWithRandomData()
                .clickColumnUserIDHeader()
                .getUserIDList();

        Assert.assertEquals(userIDList, userIDList.stream().sorted(Comparator.reverseOrder()).toList());
    }

    public String randomString() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 7);
    }

    public String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    @DataProvider(name = "usersCreateDataProvider")
    public Object[][] usersCreateDataProvider() {
        return new Object[][]{
                {"Ivan", randomString(), randomString(), randomEmail()},
                {"Maria", randomString(), randomString(), randomEmail()},
                {"Sofia", randomString(), randomString(), randomEmail()},
                {"Irina", randomString(), randomString(), randomEmail()}
        };
    }

    @Test(dataProvider = "usersCreateDataProvider")
    public void testRedirectToUserPage(String username, String password, String fullName, String email) {

        String currentUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .createUser(username, password, fullName, email)
                .clickLogo()
                .clickPeopleOnSidebar()
                .clickUser(username)
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains(username.toLowerCase()));
    }

    @Test
    public void testErrorMessageForEmptyField() {

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .createUser("", "", "", "");

        CreateUserPage createUserPage = new CreateUserPage(getDriver());

        Assert.assertNotNull(createUserPage.getUsernameErrorMsgField());
        Assert.assertNotNull(createUserPage.getPasswordErrorMsgField());
        Assert.assertNotNull(createUserPage.getFullNameErrorMsgField());
        Assert.assertNotNull(createUserPage.getEmailErrorMsgField());

    }

    @Test
    public void testFreestyleJobRemoteTriggering() {
        final String projectName = "Project1";
        final String tokenName = "tokenForProject1";

        //Precondition
        final String[] tokenUuidUser = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickUserIdLink()
                .clickConfigureOnSidebar()
                .getTokenUuidUser(tokenName);

        final String token = tokenUuidUser[0];
        final String uuid = tokenUuidUser[1];
        final String user = tokenUuidUser[2];

        new UserConfigurePage(getDriver())
                .clickSaveButton()
                .clickLogo();

        //Test steps
        final String actualConsoleLogs = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(projectName)
                .selectFreestyleAndClickOk()
                .scrollToBuildTriggersHeading()
                .clickTriggerBuildsRemotelyCheckbox()
                .inputAuthenticationToken(tokenName)
                .clickAddTimestampsCheckbox()
                .clickSaveButton()
                .triggerJobViaHTTPRequest(token, user, projectName, tokenName)
                .clickSuccessConsoleOutputButton()
                .getConsoleLogsText();

        new JobBuildConsolePage(getDriver())
                .revokeTokenViaHTTPRequest(token, uuid, user);

        Allure.step("Expected result: Build is triggered remotely");
        Assert.assertTrue(
                actualConsoleLogs.contains("Started by remote host"),
                "The build should be triggered remotely."
        );
        Assert.assertFalse(
                actualConsoleLogs.contains("Started by user"),
                "The build should NOT be triggered by user."
        );

        final List<String> uuidList = new JobBuildConsolePage(getDriver())
                .clickLogo()
                .clickPeopleOnSidebar()
                .clickUserIdLink()
                .clickConfigureOnSidebar()
                .getUuidlist();

        Allure.step("Expected result: Token is revoked");
        Assert.assertListNotContainsObject(uuidList, uuid, "Token was not revoked for this user.");
    }
}
