package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem20Test extends BaseTest {

    @Test
    public void testNewItemWithEmptyName() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        String error_Message = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
        Assert.assertEquals(error_Message, "» This field cannot be empty, please enter a valid name");
    }

}
