package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProject7Test extends BaseTest {
    final String freestyleProjectName = "FreestyleProjectTest";
    @Test
    public void testCreateFreestyleProject() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .clickSave()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(freestyleProjectName));
    }
}
