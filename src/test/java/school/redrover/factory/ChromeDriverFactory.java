package school.redrover.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverFactory extends AbstractDriverFactory {

    private final ChromeOptions chromeOptions;

    public ChromeDriverFactory() {
        this.chromeOptions = new ChromeOptions();
    }

    public ChromeDriverFactory(ChromeOptions options) {
        this.chromeOptions = options;
    }

    @Override
    protected WebDriver create() {
        return new ChromeDriver(chromeOptions);
    }
}
