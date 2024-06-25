package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.AssertUtils;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

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

        AssertUtils
                .allureAnnotation("Name 'admin' is displayed on People page")
                .listContainsObject(namesList, "admin", "People page is not correct!");
    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Small")
    public void testChangeIconSizeToSmall() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickIconS()
                .getUserIconSize();

        AssertUtils
                .allureAnnotation("Icon size on page is Small")
                .equals(actualIconSize, new Dimension(16, 16));
    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Medium")
    public void testChangeIconSizeToMedium() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickIconM()
                .getUserIconSize();

        AssertUtils
                .allureAnnotation("Icon size on page is Medium")
                .equals(actualIconSize, new Dimension(20, 20));
    }

    @Test
    @Story("US_07.003 Icon size")
    @Description("Check icon size after change to Large")
    public void testChangeIconSizeToLarge() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickIconL()
                .getUserIconSize();

        AssertUtils
                .allureAnnotation("Icon size on page is Large")
                .equals(actualIconSize, new Dimension(24, 24));
    }

    public void createUser(String username) {
        TestUtils.createUser(this, username);
    }

    @Test
    @Story("US_07.002 Sort people")
    @Description("Sort people by user ID")
    public void testSortPeopleByUserIdDescending() {
        List<String> userslist = List.of(
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

        userslist.forEach(this::createUser);

        List<String> userIDList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleUserID()
                .getUserIDList();

        AssertUtils
                .allureAnnotation("Table is sorted descending by User ID")
                .equals(userIDList, userIDList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testSortPeopleByUserIdDescending")
    @Story("US_07.002 Sort people")
    @Description("Sort people descending by full name")
    public void testSortPeopleByFullNameDescending() {

        List<String> namesList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleName()
                .getNamesList();

        AssertUtils
                .allureAnnotation("Table is sorted descending by full name")
                .equals(namesList, namesList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testSortPeopleByUserIdDescending")
    @Story("US_07.002 Sort people")
    @Description("Sort people by last commit activity")
    public void testSortPeopleByLastCommitActivityDescending() {
        List<String> lastCommitActivityList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleLastCommitActivity()
                .getLastCommitActivityList();

        AssertUtils
                .allureAnnotation("Table is sorted descending by last commit activity")
                .equals(lastCommitActivityList,
                        lastCommitActivityList.stream().sorted(Collections.reverseOrder()).toList());
    }

    @Test(dependsOnMethods = "testSortPeopleByUserIdDescending")
    @Story("US_07.002 Sort people")
    @Description("Sort people by on/off state value")
    public void testSortPeopleByStateDescending() {
        List<String> onOffList = new HomePage(getDriver())
                .clickPeopleOnSidebar()
                .clickTitleOn()
                .getOnOffList();

        AssertUtils
                .allureAnnotation("Table is sorted descending by state")
                .equals(onOffList, onOffList.stream().sorted(Collections.reverseOrder()).toList());
    }
}
