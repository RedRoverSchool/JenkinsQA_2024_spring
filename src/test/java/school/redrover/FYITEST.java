package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FYITEST {
        // Testing that download button redirects user to App store preview
        @Test
        public void download()  {
            WebDriver driver = new ChromeDriver();
            driver.get("https://fyi.fyi/");
            WebElement link = driver.findElement(By.xpath("//a[@href='https://fyi.me/app'] "));
            link.click();
            WebElement Appstore = driver.findElement(By.className("we-localnav__title__product"));
            String value = Appstore.getText();
            Assert.assertEquals(value,"Mac App Store" );
            String currentURL = driver.getCurrentUrl();
            Assert.assertEquals(currentURL, "https://apps.apple.com/us/app/fyi-ai-focus-your-ideas/id1554341467");
            driver.quit();}

    }

