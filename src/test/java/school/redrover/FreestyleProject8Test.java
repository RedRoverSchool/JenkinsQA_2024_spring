package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject8Test extends BaseTest {

        public void createFreestyleProject() {

            getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
            getDriver().findElement(By.id("name")).sendKeys("Freestyle project");
            getDriver().findElement(By.xpath(
                    "//*[@class='hudson_model_FreeStyleProject']")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
        }
    @Test
    public void testRenamingFreestyleProject() {
        String text;

        createFreestyleProject();
        getDriver().findElement(By.xpath("//*[contains(@href, 'rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath(
                "//*[@name='newName']")).sendKeys("New name");
        getDriver().findElement(By.xpath("//*[@name = 'Submit']")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text, "New name");
    }

}
