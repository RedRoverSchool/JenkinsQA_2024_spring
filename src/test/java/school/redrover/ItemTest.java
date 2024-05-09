package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class ItemTest extends BaseTest {

    @Test
    public void testElementPeople() {
        new ItemPage(getDriver())
                .ElementPeopleClick();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']")).getText(), "People");
    }

    @Test
    public void testElementWelcome() {
        new ItemPage(getDriver())
                .ElementWelcomeClic();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'Welcome to Jenkins!')]")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testAddItem() {
        new ItemPage(getDriver())
                .ElementPeopleClick()
                .NewItemClick()
                .FreestyleProjectClick()
                .NewItemName()
                .clickButtonOK()
                .clickLogo();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(.,'NewItemName')]")).getText(), "NewItemName");

    }

    @DataProvider(name = "userAddProvider")
    public Object[][] userAddProvider() {
        return  new Object[][] {
                {"Gleb", "Password", "Full name", "E-mail@com"},
                {"Ignat", "Password", "Full name", "E-mail@com"},
                {"Lion", "Password", "Full name", "E-mail@com"},
                {"Oleg", "Password", "Full name", "E-mail@com"},
                {"Den", "Password", "Full name", "E-mail@com"},
        };
    }

    @Test(dataProvider = "userAddProvider")
    public void testCheckPeople(String Username, String Password, String Fullname, String Email) {

        List<String> showNam = new HomePage(getDriver())
                .clickManageJenkins()
                .clickUsers()
                .createUser(Username, Password, Fullname, Email)
                .getUserIDList();
//        System.out.printf(showNam.toString());

        Assert.assertTrue(showNam.contains(Username));
    }
}
