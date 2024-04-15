package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CrtPrjTest extends BaseTest {
    @Test
    public void testCrtProject() {
        String text;

        getDriver().findElement(By.className("trailing-icon")).click();
        getDriver().findElement(By.id("name")).sendKeys("Freestyle");
        getDriver().findElement(By.xpath(
                "//*[contains(text(),'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath(
                "//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(
                "Freestyle", Keys.ENTER);

        getDriver().findElement(By.xpath(
                "//*[@href='/job/Freestyle/configure']")).click();
        text = getDriver().findElement(By.xpath("//h2")).getText();

        Assert.assertEquals(text,"General");

    }
}
