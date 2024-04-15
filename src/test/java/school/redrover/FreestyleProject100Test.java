package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class FreestyleProject100Test extends BaseTest {

    @Test
    public void testDeleteUsingDropdown() {
        final String projectName = "This is the project to be deleted";

        createNewItemAndReturnToDashboard(this, projectName, Item.FREESTYLE_PROJECT);

        deleteUsingDropdown(this, projectName);

        Assert.assertTrue(getDriver().findElement(EMPTY_STATE_BLOCK).isDisplayed());
    }
}
