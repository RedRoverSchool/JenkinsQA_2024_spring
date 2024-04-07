package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import static org.testng.Assert.assertEquals;

@Ignore
public class WikiTest extends BaseTest {

    @Test
    public void testWiki() {
        getDriver().get("https://www.wikipedia.org/");

        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = getDriver().findElement(By.id("searchInput"));
        WebElement searchInputButton = getDriver().findElement(By.xpath("//*[@id='search-form']/fieldset/button"));

        textBox.sendKeys("test");
        searchInputButton.click();

        WebElement message = getDriver().findElement(By.xpath("//*[@id='firstHeading']/span"));
        String value = message.getText();
        assertEquals(value, "Test");
    }
}
