package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject8Test extends BaseTest {
    @Test
    public void testCreateProject(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Freestyle project");
        getDriver().findElement(By.xpath("//*[contains(@class,'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getWait10();
        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//*[@id='main-panel']/div[1]/div/h1"))
                .getText(), "Freestyle project");
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

    @Test(dependsOnMethods = "testCreateProject")
    public void testFreestyleProjectMoveToFolder() {
        TestUtils.createNewJob(this,TestUtils.Job.FOLDER,"Folder");

        getDriver().findElement(By.xpath("//*[@href='job/Freestyle%20project/']/span")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Freestyle%20project/move']")).click();
        getDriver().findElement(By.xpath("//select/option[@value='/Folder']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        TestUtils.goToMainPage(getDriver());
        getWait10();
        TestUtils.clickAtBeginOfElement(this, TestUtils.getViewItemElement(this, "Folder"));

        getWait10();
        String projectNameText = getDriver().findElement(By.xpath("//*[@href='job/Freestyle%20project/']")).getText();
        Assert.assertEquals(projectNameText, "Freestyle project");

    }
}
