package school.redrover.factory;

import org.openqa.selenium.chrome.ChromeOptions;
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
    FIREFOX {
        @Override
        public FirefoxDriverFactory getDriverFactory() {
            return new FirefoxDriverFactory();
        }
    };

    public static AbstractDriverFactory getFactory(FirefoxOptions options) {
        return new FirefoxDriverFactory(options);
    }

    public static AbstractDriverFactory getFactory(ChromeOptions options) {
        return new ChromeDriverFactory(options);
    }

    abstract DriverFactory getDriverFactory();
}
