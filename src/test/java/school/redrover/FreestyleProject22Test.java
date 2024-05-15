package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class FreestyleProject22Test extends BaseTest {
    private static final String PROJECT_NAME = "Freestyle_first";
    private static final String PROJECT_DESCRIPTION = "my new build";

    @Test
    public void testEditDescription(){
        final String editDescribe = "Create one more build apps";

        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickConfigure()
                .setDescription(PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .clearDescription()
                .setDescription(editDescribe)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertTrue(projectDescriptionText.contains(editDescribe));
    }
}
