package school.redrover.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverManager {

    public static WebDriver createDriver(String browser, String options) {
        List<String> arguments = options != null ? Arrays.asList(options.split(";")) : new ArrayList<>();

        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                arguments.forEach(arg -> setOptions(arg, firefoxOptions));
                yield BrowserFactory.getFactory(firefoxOptions).getDriver();
            }
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                arguments.forEach(chromeOptions::addArguments);
                yield BrowserFactory.getFactory(chromeOptions).getDriver();
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                arguments.forEach(edgeOptions::addArguments);
                yield BrowserFactory.getFactory(edgeOptions).getDriver();
            }
            default -> BrowserFactory.fromString(browser).getDriverFactory().getDriver();
        };
    }

    private static void setOptions(String arg, FirefoxOptions firefoxOptions) {
        if (arg.startsWith("--window-size=")) {
            String[] size = arg.substring("--window-size=".length()).split(",");
            if (size.length == 2) {
                firefoxOptions.addArguments("--width=" + Integer.parseInt(size[0]));
                firefoxOptions.addArguments("--height=" + Integer.parseInt(size[1]));
            }
        } else {
            firefoxOptions.addArguments(arg);
        }
    }
}
