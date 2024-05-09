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

}
