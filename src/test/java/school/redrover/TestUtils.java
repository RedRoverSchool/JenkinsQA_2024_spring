package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class TestUtils {
    private static final By BY_USER_ID = By.xpath("//a[contains(@href, 'user')]");

    public static String getUserID(WebDriver driver) {
        return driver.findElement(BY_USER_ID).getText();
    }
}
