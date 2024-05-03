package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem20Test extends BaseTest {

    @Test
    public void testNewItemOpens(){
        getDriver().findElement(By.xpath("//a[contains(@href, '/newJob')]")).click();

        String newItemHeader = getDriver().getTitle();
        String TextAboveSearchField = getDriver().findElement(By.xpath("//*[@class='add-item-name']/label")).getText();

        Assert.assertEquals(newItemHeader, "New Item [Jenkins]");
        Assert.assertEquals(TextAboveSearchField, "Enter an item name");
    }
    @Test(dependsOnMethods = "testNewItemOpens")
    public void testNewItemWithEmptyName(){
        getDriver().findElement(By.xpath("//*[@class='task-icon-link']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        String error_Message = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
        Assert.assertEquals(error_Message, "» This field cannot be empty, please enter a valid name");
    }
}
