package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject21Test extends BaseTest {
    @Test
    public void testAddDescription() {

        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "New Project", this);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.cssSelector("[name='description']")).sendKeys("Test input");
        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[id='description'] div")).getText(),
                "Test input");
    }
}
