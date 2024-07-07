package school.redrover.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverManager {

    public static DriverFactory getFactory(FirefoxOptions options) {
        return BrowserFactory.getFactory(options);
    }

    public static DriverFactory getFactory(ChromeOptions options) {
        return BrowserFactory.getFactory(options);
    }

    public static DriverFactory getFactory(String browser, String options) {
        final String[] split = options.split(";");
        return switch (browser.toLowerCase()) {
            case "firefox" -> getFactory(new FirefoxOptions().addArguments(split));
            case "chrome" -> getFactory(new ChromeOptions().addArguments(split));
            default -> BrowserFactory.fromString(browser).getDriverFactory();
        };
    }
}
