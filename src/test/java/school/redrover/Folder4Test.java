package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class Folder4Test extends BaseTest {

    @Test
    public void testCreateNewFolder() {
        getDriver().findElement(By.cssSelector("[href *= 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFolder");
        getDriver().findElement(By.cssSelector("[class *= 'plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        returnToDashBoard(this);

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='NewFolder']")).isDisplayed());
    }
}
