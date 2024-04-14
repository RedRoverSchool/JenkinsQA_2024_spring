package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class TestUtils {

    public static String getUserID(WebDriver driver) {
        return driver.findElement(By.xpath("//a[contains(@href, 'user')]")).getText();
    }
}
