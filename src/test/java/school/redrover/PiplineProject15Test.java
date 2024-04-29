package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PiplineProject15Test extends BaseTest {

    @Test
    public void testCreatePipline() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("PiplineOne");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"job_PiplineOne\"]/td[3]/a")).getText(),
                "PiplineOne");
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testFindPiplineProject() {

        getDriver().findElement(By.xpath("//*[@id=\"search-box\"]")).sendKeys("PiplineOne");
        getDriver().findElement(By.xpath("//*[@id=\"search-box\"]")).sendKeys(Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div/h1")).getText(),
                "PiplineOne");
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testCreatePiplineSameName() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("PiplineOne");

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText(),
                "» A job already exists with the name ‘PiplineOne’");
    }
}
