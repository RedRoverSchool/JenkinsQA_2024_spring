package school.redrover.runner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyViewsTest extends BaseTest{
    @Test
    public void testCreateNewView() {

            getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
            getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                    .sendKeys("MultiConfigurationProject");
            getDriver().findElement(By.xpath("//span[contains(text(),  'Multi-configuration project')]")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
            getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]/span/a")).click();
        getDriver().findElement(By.xpath("//a[@tooltip ='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewView");
        getDriver().findElement(By.xpath("//label[text() = 'My View']")).click();
        getDriver().findElement(By.id("ok")).click();
        String result = getDriver().findElement(By.xpath("//div[@class='tab active']"))
                .getText();

        Assert.assertEquals(result, "NewView");
    }
}
