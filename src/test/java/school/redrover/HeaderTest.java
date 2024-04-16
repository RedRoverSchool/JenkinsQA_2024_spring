package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testLogoJenkins() {

    getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
    getDriver().findElement(By.id("jenkins-name-icon")).click();

    Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testSearchBox() {

      getDriver().findElement(By.id("search-box")).sendKeys("ma");
      getDriver().findElement(By.className("yui-ac-bd")).click();

      Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='yui-ac-content']//li[1]")).getText(),
              "manage");

      getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);

      Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Manage Jenkins");
    }
    }



