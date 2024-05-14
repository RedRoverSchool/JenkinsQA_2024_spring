package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;


public class FreestyleProject15Test extends BaseTest {

    @Test
    public void testFirst() {
        final String firstJobName = "First job";
        final String secondJobName = "Second job";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(firstJobName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickLogo()
                .clickNewItem()
                .setItemName(secondJobName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickLogo()
                .getItemList();

        Assert.assertEquals(itemList, List.of(firstJobName, secondJobName));
    }
}

