package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class MulticonfigurationProject1Test extends BaseTest {
    @Test
    public void testSearchForCreatedProject(){
        final String projectName = "NewMulticonfigurationProject";

        getDriver().findElement(By.xpath("//*[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(projectName);
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + projectName + "/"));
    }
}
