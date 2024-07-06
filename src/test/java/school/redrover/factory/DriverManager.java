package school.redrover.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverManager {

    public static AbstractDriverFactory getFactory(FirefoxOptions options) {
        return BrowserFactory.getFactory(options);
    }

    public static AbstractDriverFactory getFactory(ChromeOptions options) {
        return BrowserFactory.getFactory(options);
    }
}
