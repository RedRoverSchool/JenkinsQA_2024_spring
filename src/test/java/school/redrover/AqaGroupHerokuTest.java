package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AqaGroupHerokuTest extends AqaGroupBaseTest {

    @Test
    public void testHiddenElement() {
        getDriver().get("https://the-internet.herokuapp.com/dynamic_loading/1");

        getDriver().findElement(By.tagName("button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        Assert.assertEquals(
                getDriver().findElement(By.id("finish")).getText(),
                "Hello World!",
                "error");
    }

    @Test
    public void testRenderedElement() {
        getDriver().get("https://the-internet.herokuapp.com/dynamic_loading/2");

        getDriver().findElement(By.tagName("button")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        Assert.assertEquals(
                getDriver().findElement(By.id("finish")).getText(),
                "Hello World!",
                "error");
    }

    @Test
    public void testAddElement() {
        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        getDriver().findElement(By.cssSelector(".example button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("added-manually")).getText(),
                "Delete",
                "not added delete");
    }

    @Test
    public void testDeleteElement() {
        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        getDriver().findElement(By.cssSelector(".example button")).click();
        getDriver().findElement(By.cssSelector(".example button")).click();
        getDriver().findElement(By.className("added-manually")).click();
        getDriver().findElement(By.className("added-manually")).click();

        Assert.assertTrue(
                getDriver().findElements(By.className("added-manually")).isEmpty(),
                "delete(s) present");
    }

    @Test
    public void testAuth() {
        getDriver().get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        Assert.assertEquals(
                getDriver().findElement(By.tagName("p")).getText(),
                "Congratulations! You must have the proper credentials.");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testFindBrokenPic() {
        getDriver().get("https://the-internet.herokuapp.com/broken_images");

        List<WebElement> lst = getDriver().findElements(By.xpath("//*[@class ='example']/img"));

        Assert.assertFalse(lst.isEmpty());

        for (WebElement pic : lst) {
            Assert.assertNotEquals(
                    pic.getDomProperty("naturalWidth"),
                    "0",
                    "broken image is detected");
        }
    }

    @Test
    public void testCheckBox() {
        getDriver().get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkBox1 = getDriver().findElement(By.xpath("//*[@type = 'checkbox']"));
        WebElement checkBox2 = getDriver().findElement(By.xpath("//*[@type = 'checkbox'][2]"));
        checkBox1.click();
        checkBox2.click();

        Assert.assertTrue(
                checkBox1.isSelected(),
                "checkbox 1 is not checked");
        Assert.assertFalse(
                checkBox2.isSelected(),
                "checkbox 2 is checked");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testFindBrokenPicTestFinal() {
        getDriver().get("https://the-internet.herokuapp.com/broken_images");

        LogEntries logs = getDriver().manage().logs().get(LogType.BROWSER);

        Assert.assertTrue(logs.getAll().size() > 0);
        for (LogEntry entry : logs) {
            Assert.assertTrue(
                    entry.getMessage().contains("Failed to load resource: the server responded with a status of 404"),
                    "broken image is detected");
        }
    }
}