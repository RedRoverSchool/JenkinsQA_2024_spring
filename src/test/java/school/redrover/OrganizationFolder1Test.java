package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.OrganizationFolderConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class OrganizationFolder1Test extends BaseTest {

   @Test
    public void testCreate() {

        final String organizationFolderName = "Organization Folder";

        List <String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.ORGANIZATION_FOLDER, new OrganizationFolderConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();
        Assert.assertTrue(itemList.contains(organizationFolderName));
    }
}
