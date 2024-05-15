package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject9Test extends BaseTest {
    private final String freestyleProjectName = "Freestyle Project";
    private final String expectedFreestyleProjectDescription = "This is very important freestyle project";

    @Test
    public void testCreateFreestyleProjectWithoutDescription() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertFalse(itemList.isEmpty());
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        String projectDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .setDescription(expectedFreestyleProjectDescription)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescription, expectedFreestyleProjectDescription);
    }

    @Test
    public void testOpenFreestyleProject() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .getProjectName();

        Assert.assertEquals(projectName, freestyleProjectName);
    }
}
