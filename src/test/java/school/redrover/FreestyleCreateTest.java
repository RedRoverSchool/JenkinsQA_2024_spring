package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;


public class FreestyleCreateTest extends BaseTest {

    @Test
    public void testFreestyleCreate() {

        final String projectName = "project1";

        String itemName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectNameFromBreadcrumbs();

        Assert.assertEquals(itemName, projectName);
    }

    @Test
    public void testCreateFreestyleProject() {

        final String projectName = "TestName";

        String itemName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectNameFromBreadcrumbs();

        Assert.assertEquals(itemName, projectName);
    }
}


