package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
public class PeopleIconSizeTest extends BaseTest {
    @Test
    public void testIconSizeSmall() {
        getDriver().findElement(By.cssSelector("[href='/asynchPeople/']")).click();
        getDriver().findElement(By.cssSelector("[title='Small']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div > svg:nth-child(1)")).getSize(),
                new Dimension(16, 16));
    }
    @Test
    public void testIconSizeMedium() {
        getDriver().findElement(By.cssSelector("[href='/asynchPeople/']")).click();
        getDriver().findElement(By.cssSelector("[tooltip='Medium']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div > svg:nth-child(1)")).getSize(),
                new Dimension(20, 20));
    }
    @Test
    public void testIconSizeLarge() {
        getDriver().findElement(By.cssSelector("[href='/asynchPeople/']")).click();
        getDriver().findElement(By.cssSelector("[title='Large']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div > svg:nth-child(1)")).getSize(),
                new Dimension(24, 24));
    }
}
