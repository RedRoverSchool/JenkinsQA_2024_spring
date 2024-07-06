package school.redrover.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverFactory extends AbstractDriverFactory {

    private final FirefoxOptions firefoxOptions;

    public FirefoxDriverFactory() {
        firefoxOptions = new FirefoxOptions();
    }

    public FirefoxDriverFactory(FirefoxOptions firefoxOptions) {
        this.firefoxOptions = firefoxOptions;
    }

    @Override
    protected WebDriver create() {
        return new FirefoxDriver(firefoxOptions);
    }
}
