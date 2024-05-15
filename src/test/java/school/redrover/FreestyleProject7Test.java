package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProject7Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {
        final String freestyleProjectName = "FreestyleProjectTest";

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(freestyleProjectName));
    }
}
