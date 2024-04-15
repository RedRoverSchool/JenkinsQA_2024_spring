package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ProjectCreateTest extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {
        String text;

        getDriver().findElement(By.xpath(
                "//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test project");
        getDriver().findElement(By.xpath(
                "//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text,"Test project");

    }
}
