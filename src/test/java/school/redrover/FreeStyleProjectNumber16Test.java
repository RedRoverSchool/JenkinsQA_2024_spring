package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreeStyleProjectNumber16Test extends BaseTest {

    @Test
    public void testFreeStyleProjectExists() {
        String projectName = "New FreeStyle Project";

        List<String> name = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk().clickLogo()
                .getItemList();

        Assert.assertTrue(name.contains(projectName));
    }
}
