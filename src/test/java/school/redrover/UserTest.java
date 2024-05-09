package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
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
    public void testCreateUserViaManageJenkins() {
        List<String> userName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsers()
                .clickCreateUser()
                .setUserName(USER_NAME)
                .setPassword(PASSWORD)
                .setConfirmPassword(PASSWORD)
                .setFullName(FULL_NAME)
                .setEmailAddress(EMAIL_ADDRESS)
                .clickCreateUser()
                .getUsersList();

        Assert.assertTrue(userName.contains("TestUser"));
    }

    @Test
    public void testUsersSortingByName() {

        List<String> names = new HomePage(getDriver())
                .clickManageJenkins().clickUsers()
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
                .clickManageJenkins().clickUsers()
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
        return new Object[][] {
//                {"Username", "Password", "Full name", "E-mail address"},
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
                .clickUsers()
                .createUser(username, password, fullName, email)
                .clickLogo()
                .clickPeopleButton()
                .clickUser(username)
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains(username.toLowerCase()));
    }
}