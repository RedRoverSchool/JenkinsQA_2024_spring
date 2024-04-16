package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameProjectWithSidebarTest extends BaseTest {

    @Test
    public void  renameWithSidebar() {
        String text;

        getDriver().findElement(By.xpath(
                "//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Freestyle project");
        getDriver().findElement(By.xpath(
                "//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='side-panel']/div/div[7]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath(
                "//*[@name='newName']")).sendKeys("New name");
        getDriver().findElement(By.xpath("//*[@name = 'Submit']")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text,"New name");

    }
}
