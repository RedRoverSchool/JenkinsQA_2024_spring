package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PeoplePage;
import school.redrover.model.UsersPage;
import school.redrover.runner.BaseTest;

import java.util.Collections;
import java.util.List;

@Epic("People")
public class PeopleTest extends BaseTest {

    @Test
    @Story("US_07.001 View people")
    @Description("Check name 'admin' on People page")
    public void testDisplayedNameOnPeoplePage() {

        List<String> namesList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .getNamesList();

        Assert.assertListContainsObject(namesList, "admin", "People page is not correct!");
    }

    @Test
    @Story("US_07.002 Sort people")
    @Description("Sort people by UserID, Name, Last Commit Activity, On/Off Value")
    public void testSortPeople() {

        final List<String> userNamesList = List.of(
                "johndoe21",
                "janesmith1985",
                "david_lee92",
                "emily_williams",
                "alex_johnson",
                "chris_evans",
                "mary_jones",
                "michael_brown",
                "steve_rogers",
                "lisa_taylor");

        new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink();

        for (String userName : userNamesList) {

            new UsersPage(getDriver())
                    .clickCreateUser()
                    .clearUserNameField()
                    .typeUserName(userName)
                    .setPassword(userName)
                    .setConfirmPassword(userName)
                    .setFullName(userName.replaceAll("\\d", ""))
                    .setEmailAddress(userName + "@example.com")
                    .clickCreateUser();
        }

        List<String> expectedUserIDList = new UsersPage(getDriver())
                .clickLogo()
                .clickPeopleOnSidebar()
                .getUserIDList()
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        List<String> actualUserIDList = new PeoplePage(getDriver())
                .clickTitleUserID()
                .getUserIDList();


        List<String> expectedNamesList = new PeoplePage(getDriver())
                .getNamesList()
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        List<String> actualNamesList = new PeoplePage(getDriver())
                .clickTitleName()
                .getNamesList();


        List<String> expectedLastCommitActivityList = new PeoplePage(getDriver())
                .getLastCommitActivityList()
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        List<String> actualLastCommitActivityList = new PeoplePage(getDriver())
                .clickTitleLastCommitActivity()
                .getLastCommitActivityList();


        List<String> expectedOnOffList = new PeoplePage(getDriver())
                .getOnOffList()
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        List<String> actualOnOffList = new PeoplePage(getDriver())
                .clickTitleOn()
                .getOnOffList();

        Assert.assertEquals(actualUserIDList, expectedUserIDList);
        Assert.assertEquals(actualNamesList, expectedNamesList);
        Assert.assertEquals(actualLastCommitActivityList, expectedLastCommitActivityList);
        Assert.assertEquals(actualOnOffList, expectedOnOffList);

    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Small")
    public void testChangeIconSizeToSmall() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickSmallIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(16, 16));
    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Medium")
    public void testChangeIconSizeToMedium() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickMediumIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(20, 20));
    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Large")
    public void testChangeIconSizeToLarge() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickLargeIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(24, 24));
    }

    @DataProvider(name = "usernamesDataProvider")
    public Object[][] usernamesDataProvider() {
        return new Object[][]{
                {"johndoe21"}, {"janesmith1985"}, {"david_lee92"},
                {"emily_williams"}, {"alex_johnson"}, {"chris_evans"},
                {"mary_jones"}, {"michael_brown"}, {"steve_rogers"}, {"lisa_taylor"}
        };
    }

    @Test(dataProvider = "usernamesDataProvider")
    public void testRedirectToUserPage(String username) {
        List<String> userIDList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsersLink()
                .clickCreateUser()
                .typeUserName(username)
                .setPassword(username)
                .setConfirmPassword(username)
                .setFullName(username.replaceAll("_", " "))
                .setEmailAddress(username + "@domain.com")
                .clickCreateUser()
                .getUserIDList();

        Allure.step("Expected result:  Username is present on page");
        Assert.assertTrue(userIDList.contains(username));
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_07.002 Sort people")
    @Description("Sort people by UserID")
    public void testSortPeopleByUserIdDescending() {
        List<String> userIDList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleUserID()
                .getUserIDList();

        Assert.assertEquals(userIDList, userIDList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_07.002 Sort people")
    @Description("Sort people descending by full name")
    public void testSortPeopleByFullNameDescending() {

        List<String> namesList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleName()
                .getNamesList();

        Assert.assertEquals(namesList, namesList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_07.002 Sort people")
    @Description("Sort people by Last Commit Activity")
    public void testSortPeopleByLastCommitActivityDescending() {
        List<String> lastCommitActivityList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleLastCommitActivity()
                .getLastCommitActivityList();

        Assert.assertEquals(
                lastCommitActivityList,
                lastCommitActivityList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testRedirectToUserPage")
    @Story("US_07.002 Sort people")
    @Description("Sort people by On/Off Value")
    public void testSortPeopleByStateDescending() {
        List<String> onOffList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleOn()
                .getOnOffList();

        Assert.assertEquals(onOffList, onOffList.stream().sorted(Collections.reverseOrder()).toList());
    }
}
