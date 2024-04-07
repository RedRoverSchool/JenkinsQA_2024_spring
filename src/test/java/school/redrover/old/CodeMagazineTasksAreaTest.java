package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class CodeMagazineTasksAreaTest {

    @Test
    public void testTasksArea() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        Action myAction = new Actions(driver).doubleClick(searchArea).build();
        myAction.perform();

        WebElement foundText = driver.findElement(By.xpath("(//h1[@class='search__title'])"));
        String foundSearchTitle = foundText.getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();

    }

}
