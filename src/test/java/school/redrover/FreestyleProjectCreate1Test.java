package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;




public class FreestyleProjectCreate1Test extends BaseTest {
    private final static String FREESTYLE_PROJECT_NAME = "FreeStyleFirst";

    @Test
    public void testFreestyleProjectCreate() {
        String freestyleName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(freestyleName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testFreestyleProjectCreate")
    public void testErrorMessage() {

       String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .getErrorMessageInvalidCharacterOrDuplicateName();

       Boolean isEnabled = new CreateNewItemPage(getDriver()).okButtonIsEnabled();

       getDriver().findElement(By.id("name")).sendKeys(Keys.ENTER);

       Assert.assertEquals(errorMessage,"» A job already exists with the name ‘FreeStyleFirst’");
       Assert.assertFalse(isEnabled);
    }

    @Test(dependsOnMethods = "testErrorMessage")
    public void testDeleteProject() {

        boolean isItemDeleted = new HomePage(getDriver())
                .clickDeleteItemAndConfirm(FREESTYLE_PROJECT_NAME)
                .isItemDeleted(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(isItemDeleted);
    }
}
