package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PiplineProject15Test extends BaseTest {

    @Test
    public void testCreatePipline() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(TestUtils.PIPELINE);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbBar']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.linkText(TestUtils.PIPELINE)).getText(),
                TestUtils.PIPELINE);
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testFindPiplineProject() {

        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys(TestUtils.PIPELINE);
        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys(Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                TestUtils.PIPELINE);
    }
    @Ignore
    @Test(dependsOnMethods = "testCreatePipline")
    public void testCreatePiplineSameName() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(TestUtils.PIPELINE);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText(),
                "» A job already exists with the name ‘" + TestUtils.PIPELINE + "’");
    }

    @Test
    public void testCreatePiplineEmptyName() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='itemname-required']")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }
}
