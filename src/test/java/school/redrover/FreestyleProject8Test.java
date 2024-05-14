package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject8Test extends BaseTest {

    @Test
    public void testCreateProject(){
        String name = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Freestyle project")
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.FREESTYLE_PROJECT, new FreestyleConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(name, "Freestyle project");
    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testRenamingFreestyleProject(){
        getDriver().findElement(By.xpath("//*[@href='job/Freestyle%20project/']/span")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//*[@name='newName']")).sendKeys("New name");
        getDriver().findElement(By.xpath("//*[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "New name");
    }
}
