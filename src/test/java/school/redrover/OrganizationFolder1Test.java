package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class OrganizationFolder1Test extends BaseTest {

   @Test
    public void testCreate() {

        final String organizationFolderName = "Organization Folder";

        List <String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();
        Assert.assertTrue(itemList.contains(organizationFolderName));
    }
}
