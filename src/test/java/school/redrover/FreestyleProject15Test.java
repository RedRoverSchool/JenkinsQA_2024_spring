package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FreestyleProject15Test extends BaseTest {

    @Test
    public void testFirst() {

        final String firstJobName = "First job";
        final String secondJobName = "Second job";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(firstJobName)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(secondJobName)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertEquals(itemList, List.of(firstJobName, secondJobName));
    }
}

