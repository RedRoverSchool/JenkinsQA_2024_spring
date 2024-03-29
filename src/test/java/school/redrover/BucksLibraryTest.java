package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BucksLibraryTest {
    @Test
    public void testLocationList(){

        List<String> actualLocationNames = List.of("Bensalem", "Doylestown", "Langhorne", "Levittown", "Perkasie", "Quakertown","Yardley-Makefield");
        List<String> expectedLocationNames = new ArrayList<>();

        WebDriver driver = new ChromeDriver();
        driver.get("https://buckslib.org/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//*[contains(text(),' Locations')]")).click();

        for (String locationName : actualLocationNames) {
            String locationText = driver.findElement(By.xpath("//div[@id='wp-faqp-accordion-1']/div/div[@class='faq-title']/h4[text()='" + locationName + "']"))
                    .getText();
            expectedLocationNames.add(locationText);
        }

        Assert.assertEquals(actualLocationNames,expectedLocationNames);

        driver.quit();
    }
}

