package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OperationJavaGroupTest extends BaseTest {

    @Test
    public void testFreestyleProject() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();

        WebElement nameInput = getDriver().findElement(By.id("name"));
        nameInput.click();
        nameInput.sendKeys("FreestyleProject");

        WebElement project = getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        project.click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[.='Dashboard']")).click();

        WebElement nameDashboard = getDriver().findElement(By.xpath("//span[.='FreestyleProject']"));

        Assert.assertEquals(nameDashboard.getText(), "FreestyleProject");
    }
}
