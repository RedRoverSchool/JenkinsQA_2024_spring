package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FreestyleProject33test extends BaseTest {

    @Test
    public void testCreateNewFreestyleProject() {
        String projectName = TestUtils.getUniqueName("testProject");

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[contains(text(),'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        String actualProjectName = getDriver().findElement(By.xpath("//*[@class='jenkins-app-bar']//h1")).getText();

        Assert.assertEquals(actualProjectName, projectName);
    }
}
