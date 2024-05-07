package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Comparator;
import java.util.List;

public class UserTest extends BaseTest {

    private static final String USER_NAME = "TestUser";
    private static final String PASSWORD = "test123";
    private static final String FULL_NAME = "User";
    private static final String EMAIL_ADDRESS = "test@gmail.com";

    @Test
    public void testCreateUserViaManageJenkins() {
        List<String> userName = new HomePage(getDriver()).clickManageJenkins().clickUsers().clickCreateUser().setUserName(USER_NAME).setPassword(PASSWORD).setConfirmPassword(PASSWORD).setFullName(FULL_NAME).setEmailAddress(EMAIL_ADDRESS).clickCreateUser().getUsersList();

        Assert.assertTrue(userName.contains("TestUser"));
    }

    @Test
    public void testUsersSortingByName() {

        List<String> names = new HomePage(getDriver()).clickManageJenkins().clickUsers().createUserWithRandomData().createUserWithRandomData().createUserWithRandomData().createUserWithRandomData().clickColumnNameHeader().getUserNames();

        Assert.assertEquals(names, names.stream().sorted(Comparator.reverseOrder()).toList());
    }


//    @Test
//    public void testUsersSortingByUserID() {
//
//        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
//        getDriver().findElement(By.cssSelector("[href='securityRealm/']")).click();
//
//        createUser();
//        createUser();
//        createUser();
//        createUser();
//
//        getDriver().findElement(By.cssSelector("thead th:nth-child(2)>a")).click();
//        getDriver().findElement(By.cssSelector("thead th:nth-child(2)>a")).click();
//
//        List<WebElement> elements = getDriver().findElements(By.cssSelector("tr>td:nth-child(2)"));
//
//        List<String> names = elements
//                .stream()
//                .map(WebElement::getText)
//                .toList();
//
//        Assert.assertEquals(names, names.stream().sorted().collect(Collectors.toList()));
//    }
}