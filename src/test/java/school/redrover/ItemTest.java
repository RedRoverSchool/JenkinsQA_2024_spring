package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;


public class ItemTest extends BaseTest {



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
