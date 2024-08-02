package school.redrover.runner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.factory.DriverManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public final class ProjectUtils {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PREFIX_PROP = "local.";
    private static final String PROP_HOST = PREFIX_PROP + "host";
    private static final String PROP_PORT = PREFIX_PROP + "port";
    private static final String PROP_ADMIN_USERNAME = PREFIX_PROP + "admin.username";
    private static final String PROP_ADMIN_PAS = PREFIX_PROP + "admin.password";
    private static final String CLOSE_BROWSER_IF_ERROR = PREFIX_PROP + "closeBrowserIfError";
    private static final String ENV_CHROME_OPTIONS = "CHROME_OPTIONS";
    private static final String ENV_APP_OPTIONS = "APP_OPTIONS";
    private static final String PROP_CHROME_OPTIONS = PREFIX_PROP + ENV_CHROME_OPTIONS.toLowerCase();

    private static Properties properties;

    private ProjectUtils() {
        throw new UnsupportedOperationException();
    }

    private static void initProperties() {
        if (properties == null) {
            properties = new Properties();
            if (isServerRun()) {
                properties.setProperty(PROP_CHROME_OPTIONS, System.getenv(ENV_CHROME_OPTIONS));

                if (System.getenv(ENV_APP_OPTIONS) != null) {
                    for (String option : System.getenv(ENV_APP_OPTIONS).split(";")) {
                        String[] optionArr = option.split("=");
                        properties.setProperty(PREFIX_PROP + optionArr[0], optionArr[1]);
                    }
                }
            } else {
                try {
                    InputStream inputStream = ProjectUtils.class.getClassLoader().getResourceAsStream("local.properties");
                    if (inputStream == null) {
                        log(Level.ERROR, "The \u001B[31mlocal.properties\u001B[0m file not found in src/test/resources/ directory.");
                        log(Level.ERROR, "You need to create it from local.properties.TEMPLATE file.");
                        System.exit(1);
                    }
                    properties.load(inputStream);
                } catch (IOException ignore) {
                }
            }
        }
    }

    static {
        initProperties();
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    static boolean closeBrowserIfError() {
        return Boolean.parseBoolean(properties.getProperty(CLOSE_BROWSER_IF_ERROR, "true"));
    }

    public static String getUrl() {
        return String.format("http://%s:%s/",
                properties.getProperty(PROP_HOST),
                properties.getProperty(PROP_PORT));
    }

    public static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    public static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }

    private static String getOptions() {
        return properties.getProperty(PROP_CHROME_OPTIONS);
    }

    static void acceptAlert(WebDriver driver) {
        Alert alert = ExpectedConditions.alertIsPresent().apply(driver);
        if (alert != null) {
            alert.accept();
        }
    }

    static WebDriver createDriver(String browser) {
        WebDriver driver = DriverManager.createDriver(browser, getOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        return driver;
    }

    static WebDriver createDriver() {
        return createDriver("chrome");
    }

    public static void get(WebDriver driver) {
        driver.get(getUrl());
    }

    public static void log(String str) {
        LOGGER.info(str);
    }

    public static void log(Level level, String str) {
        LOGGER.log(level, str);
    }

    public static void logf(String str, Object... arr) {
        log(String.format(str, arr));
    }

    public static void logf(Level level, String str, Object... arr) {
        log(level, String.format(str, arr));
    }

    static void takeScreenshot(WebDriver driver, String instanceName, String methodName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Paths.get("screenshots"));
            FileHandler.copy(screenshot, new File("screenshots/%s.%s.png".formatted(instanceName, methodName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
