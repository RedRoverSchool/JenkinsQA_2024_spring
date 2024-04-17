package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject8Test extends BaseTest {

    @Test
    public void testRenamingFreestyleProject() {
        String text;
        ProjectCreateTest createProject = new ProjectCreateTest(getDriver());

        createProject.projectActions("Freestyle project");
        getDriver().findElement(By.xpath("//*[contains(@href, 'rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath(
                "//*[@name='newName']")).sendKeys("New name");
        getDriver().findElement(By.xpath("//*[@name = 'Submit']")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text,"New name");
    }
}
