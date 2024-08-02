package school.redrover.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public enum BrowserFactory {

    DEFAULT {
        @Override
        public DriverFactory getDriverFactory() {
            return new ChromeDriverFactory();
        }
    },

    CHROME {
        @Override
        public DriverFactory getDriverFactory() {
            return new ChromeDriverFactory();
        }
    },

    EDGE {
        @Override
        public DriverFactory getDriverFactory() {
            return new EdgeDriverFactory();
        }
    },

    FIREFOX {
        @Override
        public DriverFactory getDriverFactory() {
            return new FirefoxDriverFactory();
        }
    };

    public static DriverFactory getFactory(FirefoxOptions options) {
        return new FirefoxDriverFactory(options);
    }

    public static DriverFactory getFactory(ChromeOptions options) {
        return new ChromeDriverFactory(options);
    }

    public static DriverFactory getFactory(EdgeOptions options) {
        return new EdgeDriverFactory(options);
    }

    abstract DriverFactory getDriverFactory();

    public static BrowserFactory fromString(String browser) {
        if (browser == null || browser.isBlank()) {
            return BrowserFactory.DEFAULT;
        }
        try {
            return BrowserFactory.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            return BrowserFactory.DEFAULT;
        }
    }
}
