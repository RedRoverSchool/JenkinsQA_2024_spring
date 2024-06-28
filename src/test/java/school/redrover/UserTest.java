package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateUserPage;
import school.redrover.model.HomePage;
import school.redrover.model.JobBuildConsolePage;
import school.redrover.model.UserConfigurePage;
import school.redrover.runner.BaseTest;

import java.util.Comparator;
import java.util.List;

@Epic("User")
public class UserTest extends BaseTest {

    private static final String USER_NAME = "TestUser";
    private static final String PASSWORD = "test123";
    private static final String FULL_NAME = "User";
    private static final String EMAIL_ADDRESS = "test@gmail.com";

    @Test
    @Story("US_13.001  Create User")
    @Description("Create user via manage jenkins page")
    public void testCreateUserViaManageJenkins() {
        List<String> userNamesList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickCreateUser()
                .typeUserName(USER_NAME)
                .setPassword(PASSWORD)
                .setConfirmPassword(PASSWORD)
                .setFullName(FULL_NAME)
                .setEmailAddress(EMAIL_ADDRESS)
                .clickCreateUser()
                .getUserIDList();

        Allure.step("Expected result:  User is present on page");
        Assert.assertTrue(userNamesList.contains("TestUser"));
    }

    @Test(dependsOnMethods = "testCreateUserViaManageJenkins")
    @Story("US_13.008  Search")
    @Description("Check search box dropdown hints for users")
    public void testSearchForUserThroughSearchBar() {

        String userFullName = new HomePage(getDriver())
                .getHeader().typeTextToSearchField(FULL_NAME)
                .getHeader().getSearchFieldText();

        Allure.step("Expected result:  User hint is present into search box dropdown");
        Assert.assertEquals(userFullName, "User");
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_13.006  Sorting")
    @Description("Check that users can be sorted descending by full name clicking 'Name' column header")
    public void testUsersSortingByFullNameDesc() {
        List<String> names = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickColumnNameHeader()
                .getUserNamesList();

        Allure.step("Expected result:  Users are sorted descending by full name");
        Assert.assertEquals(names, names.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_13.006  Sorting")
    @Description("Check that users can be sorted descending by userID clicking 'User ID' column header")
    public void testUsersSortingByUserIDDesc() {

        List<String> userIDList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickColumnUserIDHeader()
                .getUserIDList();

        Allure.step("Expected result:  Users are sorted descending by userID");
        Assert.assertEquals(userIDList, userIDList.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @DataProvider(name = "usersCreateDataProvider")
    public Object[][] usersCreateDataProvider() {
        return new Object[][]{
                {"elena", "p1elena", "elena elena", "m4Elena@domain.com"},
                {"maria", "p2maria", "maria maria", "m1Maria@domain.com"},
                {"sofia", "p3sofia", "sofia sofia", "m2Sofia@domain.com"},
                {"irina", "p4irina", "irina irina", "m3Irina@domain.com"}
        };
    }

    @Test(dataProvider = "usersCreateDataProvider")
    @Story("US_13.001  Create User")
    @Description("Check redirect to user page")
    public void testRedirectToUserPage(String username, String password, String fullName, String email) {

        String currentUrl = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickCreateUser()
                .typeUserName(username)
                .setPassword(password)
                .setConfirmPassword(password)
                .setFullName(fullName)
                .setEmailAddress(email)
                .clickCreateUser()
                .clickUser(username)
                .getCurrentUrl();

        Allure.step("Expected result:  Page URL contains username");
        Assert.assertTrue(currentUrl.contains(username));
    }


    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_13.001  Create User")
    @Description("Check create user form fields validation error messages for empty input")
    public void testErrorMessageForEmptyFields() {

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickCreateUser()
                .typeUserName("")
                .setPassword("")
                .setConfirmPassword("")
                .setFullName("")
                .setEmailAddress("")
                .clickCreateUser();

        CreateUserPage createUserPage = new CreateUserPage(getDriver());

        Allure.step("Expected result:  Username error message");
        Assert.assertNotNull(createUserPage.getUsernameErrorMsgField());

        Allure.step("Expected result:  Password error message");
        Assert.assertNotNull(createUserPage.getPasswordErrorMsgField());

        Allure.step("Expected result:  Full name error message");
        Assert.assertNotNull(createUserPage.getFullNameErrorMsgField());

        Allure.step("Expected result:  Email error message");
        Assert.assertNotNull(createUserPage.getEmailErrorMsgField());
    }

    @Test
    @Story("US_13.007  Remotely trigger a job for current user")
    @Description("Remotely trigger a Freestyle job for current user")
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
