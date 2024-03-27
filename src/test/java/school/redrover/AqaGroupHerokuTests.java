package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AqaGroupHerokuTests {

    @Test
    public void HiddenElementTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        driver.findElement(By.tagName("button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!", "error");

        driver.quit();
    }

    @Test
    public void RenderedElementTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");

        driver.findElement(By.tagName("button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!", "error");

        driver.quit();
    }

    @Test
    public void AddElementTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        driver.findElement(By.cssSelector(".example button")).click();
        Assert.assertEquals(driver.findElement(By.className("added-manually")).getText(), "Delete", "not added delete");
        driver.findElement(By.cssSelector(".example button")).click();
        driver.findElement(By.className("added-manually")).click();
        driver.findElement(By.className("added-manually")).click();

        driver.quit();
    }

    @Test
    public void deleteElementTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        driver.findElement(By.cssSelector(".example button")).click();
        driver.findElement(By.cssSelector(".example button")).click();
        driver.findElement(By.className("added-manually")).click();
        driver.findElement(By.className("added-manually")).click();

        Assert.assertTrue(driver.findElements(By.className("added-manually")).isEmpty(), "delete(s) present");

//        try {
//            driver.findElement(By.className("added-manually"));
//        } catch (NoSuchElementException e) {
//            Assert.assertNotNull(e);
//        }
//        Assert.assertFalse(driver.findElement(By.className("added-manually")).isEnabled());

        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void authTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        Assert.assertFalse(driver.findElements(By.tagName("p")).isEmpty(), "can't authorize");
//        Assert.assertTrue(driver.findElement(By.tagName("p")).getText().startsWith("Congratulations!"), "can't authorize");

        driver.quit();
    }

    @Test
    public void findBrokenPicTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");

        List<WebElement> lst = driver.findElements(By.xpath("//*[@class ='example']/img"));
        boolean broken = false;
        for (WebElement pic : lst) {
            System.out.println(pic.getDomProperty("naturalWidth"));
            if (pic.getDomProperty("naturalWidth").equals("0")) {
                broken = true;
                break;
            }
//            Assert.assertFalse(pic.getDomProperty("naturalWidth").equals("0"), "picture is not found");
//            System.out.println(((JavascriptExecutor) driver).executeScript("var data = arguments[0].naturalWidth; return data;", pic));
        }
        Assert.assertFalse(broken, "broken image is detected");

        driver.quit();
    }

    @Test
    public void checkBoxTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkBox1 = driver.findElement(By.xpath("//*[@type = \"checkbox\"]"));
        WebElement checkBox2 = driver.findElement(By.xpath("//*[@type = \"checkbox\"][2]"));
        checkBox1.click();
        checkBox2.click();

        Assert.assertTrue(checkBox1.isSelected(), "checkbox 1 is not checked");
        Assert.assertFalse(checkBox2.isSelected(), "checkbox 2 is checked");

        driver.quit();
    }

    @Test
    public void findBrokenPicTestFinal() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");

        boolean brokenImages = false;

        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logs) {
            if (entry.getMessage().contains("Failed to load resource: the server responded with a status of 404")) {
                brokenImages = true;
                System.out.println("Broken Image URL from Console Logs: " + entry.getMessage());
            }
        }

        Assert.assertTrue(brokenImages, "Broken images were not found");

        driver.quit();
    }
}