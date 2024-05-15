package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;

public class FreestyleProject14Test extends BaseTest {

    @Test
    public void testCreateFreestyleProjectWithDescription() {

        final String FREESTYLE_PROJECT_NAME = "Random freestyle project";
        final String description = "Some desc for Freestyle project";

        String projectDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .setDescription(description)
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescription, description);
    }
}
