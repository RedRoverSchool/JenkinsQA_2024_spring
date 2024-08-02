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
import school.redrover.runner.AssertUtils;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

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

        AssertUtils
                .allureAnnotation("User is present on page")
                .isTrue(userNamesList.contains(USER_NAME));

    }

    @Test(dependsOnMethods = "testCreateUserViaManageJenkins")
    @Story("US_13.008  Search")
    @Description("Check search box dropdown hints for users")
    public void testSearchForUserThroughSearchBar() {

        TestUtils.createUser(this, FULL_NAME);
        String userFullName = new HomePage(getDriver())
                .getHeader().typeTextToSearchField(FULL_NAME)
                .getHeader().getSearchFieldText();

        AssertUtils
                .allureAnnotation("User hint is present into search box dropdown")
                .equals(userFullName, "User");

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

        AssertUtils
                .allureAnnotation("Users are sorted descending by full name")
                .equals(names, names.stream().sorted(Comparator.reverseOrder()).toList());
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


        AssertUtils
                .allureAnnotation("Users are sorted descending by userID")
                .equals(userIDList, userIDList.stream().sorted(Comparator.reverseOrder()).toList());
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


        AssertUtils
                .allureAnnotation("Page URL contains username")
                .isTrue(currentUrl.contains(username));
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

        AssertUtils
                .allureAnnotation("Username error message")
                .notNull(createUserPage.getUsernameErrorMsgField());

        AssertUtils
                .allureAnnotation("Password error message")
                .notNull(createUserPage.getPasswordErrorMsgField());

        AssertUtils
                .allureAnnotation("Full name error message")
                .notNull(createUserPage.getFullNameErrorMsgField());

        AssertUtils
                .allureAnnotation("Email error message")
                .notNull(createUserPage.getEmailErrorMsgField());
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

        AssertUtils
                .allureAnnotation("Build is triggered remotely")
                .isTrue(actualConsoleLogs.contains("Started by remote host"));

        AssertUtils
                .allureAnnotation("The build should NOT be triggered by user")
                .isFalse(actualConsoleLogs.contains("Started by user"));


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
