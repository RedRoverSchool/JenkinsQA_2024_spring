package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class HeaderTest extends BaseTest {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

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

    @Test
    public void testSearchResult() {
        String searchText = "i";
        WebElement headerSearchLine = getDriver().findElement(By.xpath("//input[@id='search-box']"));
                headerSearchLine.click();
                headerSearchLine.sendKeys(searchText);
                headerSearchLine.sendKeys(Keys.ENTER);

                String actualSearchResult = getDriver().findElement(By.xpath("//h1")).getText();
                String expectedSearchResult = "Search for '%s'".formatted(searchText);

                Assert.assertEquals(actualSearchResult, expectedSearchResult);
    }
    }



