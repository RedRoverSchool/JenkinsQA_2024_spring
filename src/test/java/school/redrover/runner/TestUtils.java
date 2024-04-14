package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class TestUtils {

    public static final String FREESTYLE_PROJECT = "Freestyle project";
    public static final String PIPELINE = "Pipeline";
    public static final String MULTI_CONFIGURATION_PROJECT = "Multi-configuration project";
    public static final String FOLDER = "Folder";
    public static final String MULTIBRANCH_PIPELINE = "Multibranch Pipeline";
    public static final String ORGANIZATION_FOLDER = "Organization Folder";

    public static String getUserID(WebDriver driver) {
        return driver.findElement(By.xpath("//a[contains(@href, 'user')]")).getText();
    }

    public static void createItem(String type, String name, WebDriver driver) {
        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.xpath("//span[text()='" + type + "']")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    public static void goToMainPage(WebDriver driver) {
        driver.findElement(By.id("jenkins-name-icon")).click();
    }

}
