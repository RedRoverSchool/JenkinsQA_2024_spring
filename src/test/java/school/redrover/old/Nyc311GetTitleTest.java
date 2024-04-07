package school.redrover.old;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class Nyc311GetTitleTest extends AqaGroupBaseTest {

    @Test
    public void testGetNYC311Title(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://portal.311.nyc.gov/");

        String expectedResult = "Home  · NYC311";
        String actualTitleResult = driver.getTitle();

        Assert.assertEquals(actualTitleResult, expectedResult);

        driver.quit();
    }
}
