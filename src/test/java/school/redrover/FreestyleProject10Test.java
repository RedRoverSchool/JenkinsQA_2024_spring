package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject10Test extends BaseTest {
    private static final String NEW_PROJECT_NAME = "New freestyle project";
    private static final String PROJECT_DESCRIPTION = "This is project description";

    @Test
    public void testAddDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, PROJECT_DESCRIPTION);
    }
}
